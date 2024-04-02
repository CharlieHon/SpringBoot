package com.charlie.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // 编写方法，转发到登录页面
    @GetMapping(value = {"/", "/login"})
    public String login() {
        /*
        1. 因为引入了starter-thymeleaf场景启动器
        2. 这里就会直接使用视图解析到 thymeleaf 下的模板文件 adminLogin
         */
        return "adminLogin";
    }
}
