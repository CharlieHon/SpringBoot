package com.charlie.springboot.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
//@WebListener
public class Listener_ implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 这里可以加入 项目初始化 相关业务代码
        log.info("Listener_ contextInitialized项目初始化...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 这里可以加入相应代码...
        // 通过 ioc.stop(); 停止容器可见
        log.info("Listener_ contextDestroyed ");
    }
}
