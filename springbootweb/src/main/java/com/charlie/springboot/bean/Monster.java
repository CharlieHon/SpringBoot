package com.charlie.springboot.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Monster {
    private Integer id;
    private String name;
    private Integer age;
    private Boolean isMarried;
    private Date birth;
    private Car car;
}
