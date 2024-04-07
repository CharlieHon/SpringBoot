package com.charlie.springboot.controller;

import com.charlie.springboot.exception.AccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyErrorController {

    // 模拟一个服务器内部错误500
    @GetMapping("/err")
    public String err() {
        int i = 10 / 0;
        return "manage";
    }

    // 这里配置是POST方式请求 /err2，一会使用get方式来请求 /err2
    // 这样就会出现一个4开头的错误 405:Method Not Allow
    @PostMapping("/err2")
    public String err2() {
        return "manage";
    }

    // 编写方法，模拟一个AccessException
    // 这里可以不加注解 @RequestParam ，加上则必须传入参数name
    @GetMapping("/err3")
    public String err3(/*@RequestParam(value = "name")*/ String name) {
        // 如果用户不是tom，就认为无权访问403
        if (!"tom".equals(name)) {
            throw new AccessException("自定义的异常信息");
        }
        return "manage";    // 请求转发到 manage.html，这里看不到数据因为没有经过填充
    }
}
