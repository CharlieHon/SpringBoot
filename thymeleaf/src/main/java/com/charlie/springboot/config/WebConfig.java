package com.charlie.springboot.config;

import com.charlie.springboot.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig /*implements WebMvcConfigurer*/ {

    // 方式一
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    // 注册自定义拦截器loginInterceptor
    //    registry.addInterceptor(new LoginInterceptor())
    //            .addPathPatterns("/**")     // 拦截所有请求
    //            .excludePathPatterns("/", "/login", "/images/**");  // 指定放行路径，可以根据业务需要添加放行请求
    //}

    // 方式二
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                System.out.println("public WebMvcConfigurer webMvcConfigurer()...");
                registry.addInterceptor(new LoginInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/", "/login", "/images/**");
            }
        };
    }
}
