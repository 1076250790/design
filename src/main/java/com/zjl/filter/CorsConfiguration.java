package com.zjl.filter;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 设置映射
                .allowedOrigins("*")      // 设置允许访问的域
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")      // 允许访问的方法
                .allowCredentials(true)     // 设置是否允许携带 cookie
                .maxAge(3600)       // 3600秒 之内浏览器不必再请求数据
                .allowedHeaders("*");       // 请求头
    }
}