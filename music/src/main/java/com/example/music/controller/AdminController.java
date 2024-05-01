package com.example.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.music.domain.Admin;
import com.example.music.service.AdminService;
import com.example.music.utils.Consts;
import com.example.music.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    //判断是否登陆成功
//    @RequestMapping(value="/admin/login/status",method = RequestMethod.POST)
//    public Object loginStatus(HttpServletRequest request, HttpSession session){
//        JSONObject jsonObject = new JSONObject();
//        String name = request.getParameter("name");
//        String password = request.getParameter("password");
//        boolean flag = adminService.verifyPassword(name,password);
//        if(flag){
//            jsonObject.put(Consts.CODE,1);
//            jsonObject.put(Consts.MSG,"登陆成功");
//            session.setAttribute(Consts.NAME,name);
//            return jsonObject;
//        }
//        jsonObject.put(Consts.CODE,0);
//        jsonObject.put(Consts.MSG,"用户名或密码错误");
//        return jsonObject;
//    }

    //判断是否登陆成功
    @RequestMapping(value="/admin/login/status",method = RequestMethod.POST)
    public Map<String, Object> loginStatus(HttpServletRequest request, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        try {
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            int id = adminService.verifyPassword(name,password);
            if(id>0) {
                Map<String, String> payload = new HashMap<>();
                payload.put("name",name);
                payload.put("id",Integer.toString(id));
                String token = JWTUtils.getToken(payload);
                //token存到redis
                ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
                operations.set(token,token,7, TimeUnit.DAYS);
                map.put("id",id);
                map.put("code",1);
                map.put("state",true);
                map.put("msg","认证成功！");
                map.put("token",token);
            }
        } catch (Exception e) {
            map.put("code",0);
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/admin/updatePassword",method = RequestMethod.POST)
    public Object updatePassword(HttpServletRequest request, @RequestHeader("token") String token){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String password = request.getParameter("password").trim();
        Admin admin = new Admin();
        admin.setId(Integer.parseInt(id));
        admin.setName(name);
        admin.setPassword(password);
        boolean flag = adminService.updatePassword(admin);
        if(flag){
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            System.out.println(token);
            operations.getOperations().delete(token);
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }
}
