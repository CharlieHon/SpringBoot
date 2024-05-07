package com.charlie.springboot.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 必须要先把主程序写好，才能在test中测试
        SpringApplication.run(Application.class, args);
    }
}
