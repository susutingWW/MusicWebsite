package com.example.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.music.dao.ConsumerMapper;
import com.example.music.domain.Consumer;
import com.example.music.service.ConsumerService;
import com.example.music.utils.Consts;
import com.example.music.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //添加前端用户
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();
        String avator = request.getParameter("avator").trim();
        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }

        Consumer consumer1=consumerService.getByUsername(username);
        if(consumer1!=null){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名已存在");
            return jsonObject;
        }

        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //保存到前端用户的对象
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Byte.parseByte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);
        boolean flag = consumerService.insert(consumer);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败");
        return jsonObject;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate=new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到前端用户的对象
        Consumer consumer = new Consumer();
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Byte.parseByte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        boolean flag = consumerService.update(consumer);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    //根据id删除
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteConsumer(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        boolean flag = consumerService.delete(Integer.parseInt(id));
        return flag;
    }

    //根据主键查询
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
       return consumerService.selectByPrimaryKey(Integer.parseInt(id));
    }

    //查询所有前端用户
    @RequestMapping(value = "/allConsumer", method = RequestMethod.GET)
    public Object allConsumer(HttpServletRequest request){
        return consumerService.allConsumer();
    }

    //更新前端用户图片
    @RequestMapping(value = "/updateConsumerPic", method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        System.out.println(avatorFile);
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")
                +"avatorImages";
        File file1=new File(filePath);
        System.out.println(fileName);
        System.out.println(filePath);
        if(!file1.exists())
        {
            file1.mkdir();
        }
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath="avatorImages/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag= consumerService.update(consumer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败1");
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败2"+e.getMessage());
            System.out.println(e.getMessage());
            return jsonObject;
        }finally {
            return jsonObject;
        }
    }
//    //前端登录
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public Object login(HttpServletRequest request){
//        JSONObject jsonObject = new JSONObject();
//        String username = request.getParameter("username").trim();
//        String password = request.getParameter("password").trim();
//        if(username==null||username.equals("")){
//            jsonObject.put(Consts.CODE,0);
//            jsonObject.put(Consts.MSG,"用户名不能为空");
//            return jsonObject;
//        }
//        if(password==null||password.equals("")){
//            jsonObject.put(Consts.CODE,0);
//            jsonObject.put(Consts.MSG,"密码不能为空");
//            return jsonObject;
//        }
//
//        //保存到前端用户的对象
//        Consumer consumer = new Consumer();
//        consumer.setUsername(username);
//        consumer.setPassword(password);
//        boolean flag = consumerService.verifyPassword(username,password);
//        if(flag){
//            jsonObject.put(Consts.CODE,1);
//            jsonObject.put(Consts.MSG,"登录成功");
//            jsonObject.put("userMsg",consumerService.getByUsername(username));
//            return jsonObject;
//        }
//        jsonObject.put(Consts.CODE,0);
//        jsonObject.put(Consts.MSG,"用户名或密码错误");
//        return jsonObject;
//    }

    //前端登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        if(username==null||username.equals("")){
            map.put(Consts.CODE,0);
            map.put(Consts.MSG,"用户名不能为空");
            return map;
        }
        if(password==null||password.equals("")){
            map.put(Consts.CODE,0);
            map.put(Consts.MSG,"密码不能为空");
            return map;
        }

        //保存到前端用户的对象
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        boolean flag = consumerService.verifyPassword(username,password);
        if(flag){
            Consumer consumer2=consumerService.getByUsername(username);
            Map<String, String> payload = new HashMap<>();
            payload.put("name",username);
            payload.put("id",Integer.toString(consumer2.getId()));
            String token = JWTUtils.getToken(payload);
            //token存到redis
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,7, TimeUnit.DAYS);

            map.put("token",token);
            map.put(Consts.CODE,1);
            map.put(Consts.MSG,"登录成功");
            map.put("userMsg",consumerService.getByUsername(username));
            return map;
        }
        map.put(Consts.CODE,0);
        map.put(Consts.MSG,"用户名或密码错误");
        return map;
    }
}
