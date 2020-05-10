package com.zxh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

//加载mapper接口
@MapperScan("com.zxh.dao")
public class ZxhApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZxhApplication.class, args);
    }

}
