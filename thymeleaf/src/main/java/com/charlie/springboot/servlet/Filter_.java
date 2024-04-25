package com.charlie.springboot.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 开发Filter
 * 1. @WebFilter 标识Filter_是一个过滤器，并注入到容器
 * 2. urlPatterns = {"/css/*", "/images/*"} 表示当请求 /css/目录资源 或者 /images/目录资源时 会经过该过滤器
 * 3. 此处过滤器直接放行，放行后再经过拦截器。拦截器是否拦截要根据拦截器的拦截规则
 * 4. 过滤器Filter不需要扫描
 */
@Slf4j
//@WebFilter(urlPatterns = {"/css/*", "/images/*"})
public class Filter_ implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("--Filter--doFilter");
        // 为了方便过滤器处理的资源，输出一个url
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        log.info("过滤器处理的uri={}", req.getRequestURI());
        // 直接放行，实际开发中根据自己的业务需求决定做什么？
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("--Filter--init");
    }

    @Override
    public void destroy() {
        log.info("--Filter--destroy");
    }
}
