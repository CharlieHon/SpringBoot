package com.charlie.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController = @Controller + @ResponseBody
 * 1. 将该类标识为一个控制器
 * 2. 将该类下的所有方法返回值转换位JSON格式
 * 3. 支持 RESTful 请求
 */
@RestController
public class HiController {

    @RequestMapping("/hi")
    public String hi() {
        return "hi, Charlie!";
    }
}
