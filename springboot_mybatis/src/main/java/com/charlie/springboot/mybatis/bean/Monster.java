package com.charlie.springboot.mybatis.bean;

import lombok.Data;

import java.sql.Date;

@Data
public class Monster {
    private Integer id;
    private Integer age;
    private Date birthday;
    private String email;
    private String name;
    private String gender;
    private Double salary;
}
