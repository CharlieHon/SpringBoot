package com.charlie.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

// 要求扫描 com.charlie.springboot 包/子包下的原生方式注入的Servlet
@ServletComponentScan(basePackages = "com.charlie.springboot")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(Application.class, args);
        System.out.println("Finish!");
    }
}
