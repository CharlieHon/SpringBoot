package com.charlie.springboot.mybatis;

import com.charlie.springboot.mybatis.bean.Monster;
import com.charlie.springboot.mybatis.mapper.MonsterMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class ApplicationTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private MonsterMapper monsterMapper;

    @Test
    public void t1() {
        // class com.zaxxer.hikari.HikariDataSource(默认)
        // class com.alibaba.druid.pool.DruidDataSource
        System.out.println(jdbcTemplate.getDataSource().getClass());
    }

    @Test
    public void getMonsterById() {
        Monster monster = monsterMapper.getMonsterById(1);
        // Monster(id=1, age=20, birthday=2000-11-11, email=tom@qq.com, name=tom, gender=男, salary=5000.88)
        System.out.println(monster);
    }
}
