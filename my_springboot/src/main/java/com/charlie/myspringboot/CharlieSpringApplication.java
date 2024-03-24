package com.charlie.myspringboot;

import org.apache.catalina.startup.Tomcat;

public class CharlieSpringApplication {
    // 这里会创建Tomcat对象，关联Spring容器，并启动
    public static void run() {
        try {
            // 创建Tomcat对象
            Tomcat tomcat = new Tomcat();
            // 1. 让tomcat可以将请求转发到Spring Web容器，因此需要关联
            // 2. "/myboot"：项目的application_context，就是配置tomcat时指定的application_context
            // 3. "E:\\springboot\\my_springboot"：指定项目的目录
            tomcat.addWebapp("/myboot", "E:\\springboot\\my_springboot");
            // 设置监听端口
            tomcat.setPort(9090);
            // 启动Tomcat服务器
            tomcat.start();
            // 等待请求接入，Tomcat就会一致保持运行状态，接收客户端请求
            System.out.println("========9090等待请求========");
            tomcat.getServer().await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
