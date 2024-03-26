package com.charlie.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController     // @ResponseBody(以JSON格式返回数据，而非进行视图解析) + @Controller
@Controller
public class MonsterController {

    //@RequestMapping(value = "/monster", method = RequestMethod.GET)   // 等价写法
    @GetMapping("/monster")
    public String getMonster() {
        return "GET-查询妖怪";
    }

    @PostMapping("/monster")
    public String saveMonster() {
        return "POST-添加妖怪";
    }

    @PutMapping("/monster")
    public String putMonster() {
        return "PUT-修改妖怪";
    }

    @DeleteMapping("/monster")
    public String delMonster() {
        return "DELETE-删除妖怪";
    }

    @RequestMapping("/go")
    public String go() {
        // 1. (没有配置视图解析器时)先看controller有没有 /hello
        // 2. 如果配置了视图解析器，就去定位资源页面
        return "hello";
    }
}
