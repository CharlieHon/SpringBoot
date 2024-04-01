package com.charlie.springboot.controller;

import com.charlie.springboot.bean.Car;
import com.charlie.springboot.bean.Monster;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class ResponseController {
    // 编写方法返回Monster数据-以JSON格式
    @GetMapping("/get/monster")
    @ResponseBody
    public Monster getMonster() {
        // 说明：开发中monster对象是从DB中获取
        Monster monster = new Monster();
        monster.setId(100);
        monster.setName("奔波儿灞");
        monster.setAge(230);
        monster.setIsMarried(false);
        monster.setBirth(new Date());
        Car car = new Car();
        car.setName("普尔曼");
        car.setPrice(1600.);
        monster.setCar(car);
        return monster;
    }
}
