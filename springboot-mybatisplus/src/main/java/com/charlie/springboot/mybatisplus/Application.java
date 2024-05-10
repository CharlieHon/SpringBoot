package com.charlie.springboot.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. 使用 MapperScan 注解可以指定扫描的Mapper接口
 * 2. 属性 basePackages 可以指定多个包
 */
@MapperScan(basePackages = {"com.charlie.springboot.mybatisplus.mapper"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
