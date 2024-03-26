package com.charlie.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    // 模拟请求的路径和静态资源名冲突
    // @RequestMapping("/ikun.png")
    @RequestMapping("/hi")
    public String hi() {
        return "hi:):)";
    }
}
