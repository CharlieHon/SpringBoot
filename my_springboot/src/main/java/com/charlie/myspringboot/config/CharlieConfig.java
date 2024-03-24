package com.charlie.myspringboot.config;

import com.charlie.myspringboot.bean.Monster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * - 问题：容器如何知道要扫描那些包？
 * - 在配置类可以指定要扫描的包：@ComponentScan("com.charlie.myspringboot.controller")
 */
@Configuration
@ComponentScan("com.charlie.myspringboot.controller")
public class CharlieConfig {
    // 注入Bean-monster对象，到Spring容器
    @Bean
    public Monster monster() {
        return new Monster();
    }
}
