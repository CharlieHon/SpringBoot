package com.charlie.springboot.mybatisplus.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

/**
 * 1. @TableName注解，指定bean对应数据库中的表名，默认省略为类名首字母小写
 * 2. 如果实体类Monster和表名monster对应，可以映射上，则可以省略 @TableName
 */
@TableName(value = "monster")
@Data
public class Monster {
    private Integer id;
    private Integer age;
    // 这里通过注解来解决时区问题(根据教程走，实际格式符合)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;
    private String email;
    private String name;
    private String gender;
    private Double salary;
}
