package com.charlie;

import com.charlie.springboot.bean.Furn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
public class HiController {

    // 装配到HiController
    @Resource
    private Furn furn;

    // 需求：website属性值从application.properties的k-v中读取
    @Value("${my.website}")
    private String website;

    @RequestMapping("/hi")
    @ResponseBody
    public String hi() {
        return "hi, SpringBoot " + website;
    }

    @RequestMapping("/furn")
    @ResponseBody
    public Furn getFurn() {
        // 使用Slf4j日志输出
        log.info("furn: " + furn);  // 普通输出
        log.info("furn={}", furn);  // 占位符输出
        return furn;
    }
}
