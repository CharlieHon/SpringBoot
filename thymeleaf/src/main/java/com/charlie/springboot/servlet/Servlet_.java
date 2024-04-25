package com.charlie.springboot.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 通过继承 HttpServlet类 来开发原生的Servlet
 * 2. @WebServlet 将Servlet_对象/bean注入到容器
 * 3. urlPattern = {} 对servlet配置了 yrl-pattern
 * 4. 提示：注入的原生servlet，不会被spring-boot拦截器拦截（看springboot过滤器和拦截器顺序图）
 * 5. 对于开发的原生的Servlet，需要使用 @ServletComponentScan 指定要扫描的原生Servlet包，才会注入到spring容器
 * 6.
 */
//@WebServlet(urlPatterns = {"/servlet01", "/servlet02"})
public class Servlet_ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("hello, Servlet_!");
    }
}
