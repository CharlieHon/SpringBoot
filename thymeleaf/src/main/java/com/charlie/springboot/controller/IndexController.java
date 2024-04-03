package com.charlie.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    // 编写方法，转发到登录页面
    @GetMapping(value = {"/", "/login"})
    public String login(HttpSession session) {
        // 重新登录，清空原有信息
        session.removeAttribute("loginAdmin");
        /*
        1. 因为引入了starter-thymeleaf场景启动器
        2. 这里就会直接使用视图解析到 thymeleaf 下的模板文件 adminLogin
         */
        return "adminLogin";
    }
}
