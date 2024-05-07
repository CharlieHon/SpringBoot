package com.charlie.springboot.mybatis.service.impl;

import com.charlie.springboot.mybatis.bean.Monster;
import com.charlie.springboot.mybatis.mapper.MonsterMapper;
import com.charlie.springboot.mybatis.service.MonsterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// 需要在实现类上加上 @Service 注解，而非在MonsterService接口上加入
@Service
public class MonsterServiceImpl implements MonsterService {

    // 装配MonsterMapper对象
    @Resource
    private MonsterMapper monsterMapper;

    @Override
    public Monster getMonsterById(Integer id) {
        return monsterMapper.getMonsterById(id);
    }
}
