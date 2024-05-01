package com.example.music.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.music.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
public class TestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request) {
        ValueOperations operations=stringRedisTemplate.opsForValue();
        operations.set("username","zhangSan");
        operations.set("id","1");
        System.out.println(operations.get("id"));

        Map<String, Object> map = new HashMap<>();
        // 验证令牌  交给拦截器去做
        // 只需要在这里处理自己的业务逻辑
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        map.put("state", true);
        map.put("msg", "请求成功");
        return map;
    }

}
