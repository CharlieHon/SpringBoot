package com.charlie.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class DruidDataSourceConfig {
    // 编写方法，注入DruidDataSource
    // 注入DruidDataSource后，为什么默认的HikariDataSource就失效？
    // 1. 默认数据源是如何配置的? @ConditionalOnMissingBean({DataSource.class, XADataSource.class})
    //      解读：通过该注解，判断如果容器有DataSource Bean，就不注入默认的 HikariDataSource
    // 2. debug源码
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource dataSource() throws SQLException {
        // 1. 配置注解 @ConfigurationProperties("spring.datasource") 就可以读取到yaml配置
        // 2. 我们就不需要调用 DruidDataSource 对象的 setXxx，会自动关联
        DruidDataSource druidDataSource = new DruidDataSource();
        // druidDataSource.setUrl();
        // setFilters("stat,wall")方法用于设置Druid的内置过滤器。其中：
        //      stat：启动Druid的监控统计功能
        //      wall：启动Druid的SQL防火墙功能，用于防御SQL注入攻击
        druidDataSource.setFilters("stat,wall");
        return druidDataSource;
    }

    // 配置druid的监控页面
    @Bean
    public ServletRegistrationBean statViewServlet() {
        // 配置上是一个Servlet，通过类似Servlet的注入方法注入
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
        // 设置init-parameter，设置用户名和密码
        registrationBean.addInitParameter("loginUsername", "hsp");
        registrationBean.addInitParameter("loginPassword", "666666");
        return registrationBean;
    }

    // 配置WebStatFilter，用于采集web-jdbc关联的监听数据
    @Bean
    public FilterRegistrationBean webStatFilter() {
        // 创建WebStatFilter
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(webStatFilter);
        // 默认对所有的url请求进行监控
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        // 排除指定的url
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
