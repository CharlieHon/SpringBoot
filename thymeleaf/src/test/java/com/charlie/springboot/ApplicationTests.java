package com.charlie.springboot;

import com.alibaba.druid.support.http.StatViewServlet;
import com.charlie.springboot.bean.Furn;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 演示如何在SpringBoot中开发测试类
 */
@SpringBootTest
public class ApplicationTests {
    // 使用JdbcTemplate
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() {
        // BeanPropertyRowMapper是一个将查询结果的行映射到Java对象的类，它根据列名和对象属性名的对应关系来自动映射
        BeanPropertyRowMapper<Furn> rowMapper = new BeanPropertyRowMapper<>(Furn.class);
        List<Furn> furns = jdbcTemplate.query("select * from `furn`", rowMapper);
        for (Furn furn : furns) {
            System.out.println(furn);
        }
        // 再次输出，查看底层使用的是什么数据类型
        // class com.zaxxer.hikari.HikariDataSource
        System.out.println(jdbcTemplate.getDataSource().getClass());
    }
}
