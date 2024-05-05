package com.charlie.springboot.mybatis.mapper;

import com.charlie.springboot.mybatis.bean.Monster;
import org.apache.ibatis.annotations.Mapper;

/**
 * 在Mapper接口使用 @Mapper 就会扫描,并将 Mapper 接口对象注入
 */
@Mapper
public interface MonsterMapper {
    // 根据id返回一个Monster信息
    public Monster getMonsterById(Integer id);
}
