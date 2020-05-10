package com.zxh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* 设置url请求对应的路径
* */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

   @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //设置首页路径
        registry.addViewController("/").setViewName("index");//访问根目录
        registry.addViewController("/index").setViewName("index");//访问index请求
        registry.addViewController("/index.html").setViewName("index");//访问index.html
       registry.addViewController("/login").setViewName("login");//后台登陆页面
       registry.addViewController("/login.html").setViewName("login");//后台登陆页面

    }
}
