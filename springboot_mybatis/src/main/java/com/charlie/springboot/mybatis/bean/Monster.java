package com.charlie.springboot.mybatis.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

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
