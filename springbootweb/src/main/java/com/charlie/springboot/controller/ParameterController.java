package com.charlie.springboot.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class ParameterController {

    /** 路径参数
     * 1. /monster1/{id}/{name} 构成完整请求路径
     * 2. {id} {name} 就是占位变量
     * 3. @PathVariable("id")：这里的 "id" 和 {id} 相对应
     * Integer mid其中mid可以自定义变量名
     * 4. @PathVariable Map<String, String> map：把所有传递的值传入map
     */
    @GetMapping(value = "/monster1/{id}/{name}")
    public String pathVariable(@PathVariable("id") Integer mid,
                               @PathVariable("name") String name,
                               @PathVariable Map<String, String> map) {
        System.out.println("id: " + mid);
        System.out.println("name: " + name);
        System.out.println("map: " + map);
        return "success";
    }

    /** 请求头
     * 1. @RequestHeader("Host")：获取http请求头的host信息，不区分大小写(HOST/host)
     * 2. @RequestHeader Map<String, String> header：当不指定字段且参数类型为Map时，获取所有的请求头信息
     */
    @GetMapping("/requestHeader")
    public String requestHeader(@RequestHeader("Host") String host,
                                @RequestHeader Map<String, String> header) {
        System.out.println("host: " + host);
        System.out.println("header: " + header);
        return "success";
    }

    /** 请求参数
     * <a href="/hello?name=charlie&fruit=apple&fruit=banana">@RequestParam</a><br/><br/>
     * 1. @RequestParam(value = "name", required = true)：value值对应表单提交的字段，required=true(default)表示请求必须携带该字段
     * 2. @RequestParam(value = "fruit") List<String> fruits：对于包含多个值的字段，使用列表进行接收
     * 3. @RequestParam Map<String, String> params：将所有请求参数的值都获取到，但是此时fruit只能拿到一个
     * - username: charlie
     * - fruit: [apple, banana]
     * - params: {name=charlie, fruit=apple, address=北京, id=200}
     */
    @RequestMapping("/hello")
    public String requestParam(@RequestParam(value = "name") String username,
                               @RequestParam(value = "fruit") List<String> fruits,
                               @RequestParam Map<String, String> params) {
        System.out.println("username: " + username);
        System.out.println("fruit: " + fruits);
        System.out.println("params: " + params);
        return "success";
    }

    /** cookie
     * 当测试时浏览器没有该cookie，则返回null。可以手动设置cookie
     * 1. value = "cookie_key"：表示接收名字为cookie_key的cookie
     *      如果浏览器中携带对应的cookie，那么后面的参数 String 类型 接收到的是对应的 value
     * 2. 后面参数是 Cookie 类型，则接收到的是封装好的对应的 cookie
     * cookie_value: aPo
     * username: username, charlieHan
     * cookie1: Idea-8296f2b3=>5bf8bc6d-43c8-4cb1-bb34-d1c0686c62cd
     * cookie1: cookie_key=>aPo
     * cookie1: username=>charlieHan
     */
    @GetMapping("/cookie")
    public String cookie(@CookieValue(value = "cookie_key", required = false) String cookie_value,
                         @CookieValue(value = "username", required = false) Cookie cookie,
                         HttpServletRequest req) {
        System.out.println("cookie_value: " + cookie_value);
        if (cookie != null) {
            System.out.println("username: " + cookie.getName() + ", " + cookie.getValue());
        }

        // 通过 HttpServletRequest req 参数获取cookie
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie1 : cookies) {
            System.out.println("cookie1: " + cookie1.getName() + "=>" + cookie1.getValue());
        }
        return "success";
    }

    /** 请求体
     * <form action="/save" method="post">
     *     姓名：<input type="test" name="name"/><br/>
     *     年龄：<input type="test" name="age"/><br/>
     *     <input type="submit" value="提交"/>
     * </form>
     * 1. @RequestBody String content：获取请求体
     *  - content: name=charlie&age=23
     */
    @PostMapping("/save")
    public String requestBody(@RequestBody String content) {
        System.out.println("content: " + content);
        return "success";
    }
}
