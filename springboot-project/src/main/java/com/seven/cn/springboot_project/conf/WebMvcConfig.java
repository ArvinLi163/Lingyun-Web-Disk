package com.seven.cn.springboot_project.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 解决跨域问题
 * @author: bansheng
 * @date: 2023/03/31 11:46
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry register) {
        register.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
}
}
