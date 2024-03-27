package com.charlie.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RequestController {
    /** request域数据
     * 1. 在 login() 中给request域加数据，通过请求转发请求到 /ok 对应控制器的方法
     * 2. @RequestAttribute(value = "user", required = false)：获取request域中字段为value的值
     */
    @GetMapping("/login")
    public String login(HttpServletRequest req) {
        // 向request域中添加数据
        req.setAttribute("user", "老韩");
        // 向session域中添加数据
        req.getSession().setAttribute("website", "http://www.baidu.com");
        /* 需要启动视图解析器
        spring:
         mvc:
          view:
           suffix: .html
           prefix: /charlieRes/
         */
        return "forward:/ok";   // 请求转发到 /ok 即下面的api，主要中间没有空格！
    }

    @ResponseBody
    @GetMapping("/ok")
    public String ok(@RequestAttribute(value = "user", required = false) String username,
                     HttpServletRequest req,
                     @SessionAttribute(value = "website") String website) {
        // 获取到request域中的数据
        System.out.println("user: " + username);
        // 通过 HttpServletRequest req 获取request域中的数据
        System.out.println("通过servlet api获取username：" + req.getAttribute("user"));
        // 通过 @SessionAttribute 注解获取session值
        System.out.println("session: website-" + website);
        // 同样可以通过req获取session中的值
        System.out.println("通过servlet api获取session中的website：" + req.getSession().getAttribute("website"));
        return "success";
    }

}
