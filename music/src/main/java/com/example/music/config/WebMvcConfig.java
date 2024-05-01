package com.example.music.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//解决跨域问题
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
    @Autowired
    private JWTInterceptors JWTinterceptors;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")
                        +"img"+System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator")
        );
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")
                        +"img"+System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator")
        );
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")
                        +"img"+System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator")
        );
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")
                        +"song"+System.getProperty("file.separator")
        );
        registry.addResourceHandler("/avatorImages/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")
                        +"avatorImages"+System.getProperty("file.separator")
        );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(JWTinterceptors)
                .excludePathPatterns("/admin/login/status","/img/**","/song/**","/avatorImages/**","/consumer/add","/consumer/login","/avatorImages");  // 所有用户都放行
    }
}
