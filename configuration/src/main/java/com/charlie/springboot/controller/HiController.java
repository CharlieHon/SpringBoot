package com.charlie.springboot.controller;

import com.charlie.springboot.bean.Monster;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HiController {

    @Resource
    private Monster monster;


    @RequestMapping("/monster")
    public Monster monster() {
        return monster;
    }
}
