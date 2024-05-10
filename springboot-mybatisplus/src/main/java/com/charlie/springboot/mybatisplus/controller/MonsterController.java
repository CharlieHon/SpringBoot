package com.charlie.springboot.mybatisplus.controller;

import com.charlie.springboot.mybatisplus.bean.Monster;
import com.charlie.springboot.mybatisplus.service.MonsterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class MonsterController {

    @Resource
    private MonsterService monsterService;

    // 根据id返回相应对象
    @ResponseBody
    @GetMapping("/monster")
    public Monster getMonsterById(@RequestParam(value = "id") Integer id) {
        return monsterService.getById(id);
    }

    // 方法，返回所有monster的信息
    @ResponseBody
    @GetMapping("/list")
    public List<Monster> listMonster() {
        return monsterService.list();
    }

}
