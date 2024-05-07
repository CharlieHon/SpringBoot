package com.charlie.springboot.mybatis.controller;

import com.charlie.springboot.mybatis.bean.Monster;
import com.charlie.springboot.mybatis.service.MonsterService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MonsterController {

    // 装配一个MonsterService
    @Resource
    private MonsterService monsterService;

    /**
     * @ResponseBody：以json格式返回
     * @RequestParam(value = "id")：要求请求参数中必须包含参数名 id
     */
    @ResponseBody
    @GetMapping("/monster")
    public Monster getMonsterById(@RequestParam(value = "id") Integer id) {
        return monsterService.getMonsterById(id);
    }

}
