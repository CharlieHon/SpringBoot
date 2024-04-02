package com.charlie.springboot.controller;

import com.charlie.springboot.bean.Admin;
import com.charlie.springboot.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class AdminController {

    // 响应用户的登录请求
    @PostMapping("/login")
    public String login(Admin admin, HttpSession session, Model model) {
        // 放入model中的数据，最终会放到request域中
        // 1. 验证用户是否合法
        if (StringUtils.hasText(admin.getName()) && "666".equals(admin.getPassword())) {
            // 合法，就重定向到manage.html页面，不适用请求转发是放置刷新页面会重复提交
            // 这里使用 /manage.html 是因为这样更加明确地表示到哪个页面
            // manage.html表示要求找方法的映射路径为 manage.html
            return "redirect:/manage.html";
        } else {
            // 不合法就重新登录
            return "adminLogin";
        }
    }

    // 处理用户请求 manage.html
    @GetMapping("/manage.html")
    public String mainPage(Model model) {
        // 使用集合模拟数据，放入到request域中
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User(1, "关羽", "66666", 20, "gy@qq.com"));
        users.add(new User(2, "李自成", "66666", 42, "lzc@qq.com"));
        users.add(new User(3, "王世充", "66666", 36, "wsc@qq.com"));

        // 将数据放入到model，会自动放入到request域中
        model.addAttribute("users", users);
        return "manage";    // 这里才是视图解析器到 /templates/manage.html
    }
}