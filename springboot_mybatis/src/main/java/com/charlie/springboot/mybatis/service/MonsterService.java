package com.charlie.springboot.mybatis.service;

import com.charlie.springboot.mybatis.bean.Monster;

public interface MonsterService {
    // 根据id返回对象
    public Monster getMonsterById(Integer id);
}
