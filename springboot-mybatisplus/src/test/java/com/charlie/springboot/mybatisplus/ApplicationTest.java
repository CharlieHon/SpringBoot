package com.charlie.springboot.mybatisplus;

import com.charlie.springboot.mybatisplus.bean.Monster;
import com.charlie.springboot.mybatisplus.mapper.MonsterMapper;
import com.charlie.springboot.mybatisplus.service.MonsterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApplicationTest {

    @Resource
    private MonsterMapper monsterMapper;

    @Resource
    private MonsterService monsterService;

    @Test
    public void testMonsterMapper() {
        Monster monster = monsterMapper.selectById(1);
        System.out.println(monster);
    }

    @Test
    public void testMonsterService() {
        Monster monster = monsterService.getById(2);
        System.out.println(monster);
    }

}
