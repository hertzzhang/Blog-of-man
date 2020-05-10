package com.zxh.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
* mybatis plus 的分页配置类
* */
@Configuration
@EnableTransactionManagement//开启事务管理器
@MapperScan("com.zxh.dao")//加载mapper接口
public class PageMPConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){

        return  new PaginationInterceptor();
    }
}
