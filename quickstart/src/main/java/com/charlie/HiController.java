package com.charlie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HiController {

    // 需求：website属性值从application.properties的k-v中读取
    @Value("${my.website}")
    private String website;

    @RequestMapping("/hi")
    @ResponseBody
    public String hi() {
        return "hi, SpringBoot " + website;
    }
}
