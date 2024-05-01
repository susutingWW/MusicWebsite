package com.example.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.music.dao")
public class MusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);
//        String s = "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")
//                +"img"+System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator");
//        System.out.println(s);
    }

}
