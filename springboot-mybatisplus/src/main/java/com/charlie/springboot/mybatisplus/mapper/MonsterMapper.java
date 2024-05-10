package com.charlie.springboot.mybatisplus.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charlie.springboot.mybatisplus.bean.Monster;
import org.apache.ibatis.annotations.Mapper;

/**
 * 1. BaseMapper已经默认提供了很多的crud方法，可以直接使用
 * 2. 如果BaseMapper提供的方法不能满足业务需求，可以再开发新的方法，并在MonsterMapper.xml进行配置
 */
//@Mapper
public interface MonsterMapper extends BaseMapper<Monster> {
    // 自定义方法
    // 增加一个添加方法insert
    int insertSelective(Monster monster);

    int delByEmail(@Param("email") String email);
}
