package com.charlie.springboot.config;

import com.charlie.springboot.servlet.Filter_;
import com.charlie.springboot.servlet.Listener_;
import com.charlie.springboot.servlet.Servlet_;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * - RegisterConfig_是一个配置类
 * - proxyBeanMethods = true 默认是单例返回bean
 */
@Configuration(proxyBeanMethods = true)
public class RegisterConfig_ {
    // 以使用 RegistrationBean 方式注入
    // 注入servlet
    @Bean
    public ServletRegistrationBean servlet_() {
        // 创建原生的Servlet对象
        Servlet_ servlet_ = new Servlet_();
        // 把 servlet_ 对象关联到 ServletRegistrationBean 对象
        // "/servlet03", "servlet04" 就是注入的Servlet的 urlPattern
        return new ServletRegistrationBean(servlet_, "/servlet03", "/servlet04");
    }

    @Bean
    public FilterRegistrationBean filter_() {
        Filter_ filter = new Filter_();     // 创建原生Filter
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(filter);
        // 设置filter的urlPattern
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/css/*", "/images/*"));
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean listener_() {
        // 创建原生的Listener对象
        Listener_ listener = new Listener_();
        return new ServletListenerRegistrationBean(listener);
    }
}
