## WEB开发-静态资源访问

1. 只要静态资源放在**类路径**下：`/static`,`/public`,`/resources`,`/META-INF/resources`，就可以被直接访问。
   - 配置文件`WebProperties.java`
   - ![img.png](img.png)
2. 常见静态资源：JS、CSS、图片(.jpg,.png,.bmp,.svg)、字体文件(Fonts)等
3. 访问方式：`项目根路径/+静态资源名`(默认)，比如`http://localhost:8080/hi.png`
   - 设置静态资源访问路径：`WebMvcProperties.java`
   - `this.staticPathPattern = "/**";`

### 注意事项和细节

1. **静态资源访问原理**：静态映射是`/**`，是对所有请求拦截。请求进来，先看Controller能不能处理，不能处理的请求交给**静态资源处理器**，
   如果静态资源找不到则响应404页面
2. 改变静态资源访问前缀：比如希望 `http://localhost:8080/charlieRes/...` 去请求静态资源。如在*静态资源访问前缀和控制器请求路径冲突*时
3. 改变默认的静态资源路径，如希望在类路径下新增`charlie`目录作为静态资源路径
   - 参看WebProperties.java下setStaticLocations方法
   - **配置`static-locations`会覆盖掉原有路径**
   - 因此如果要保留原来资源路径，则需要在配置的时候再加上去

```yaml
spring:
  mvc:
    # 修改静态资源访问的路径/前缀
    static-path-pattern: /charlieRes/**
    
  web:
    # 修改/增加静态资源路径
    resources:
      static-locations:   # private String[] staticLocations;
        # 修改/指定静态资源访问的路径/位置
        - "classpath:/charlie/"
        # 添加新路径/位置后，原来位置被破坏。如果需要，仍需要进行指定
        - "classpath:/META-INF/resources/"
        - "classpath:/resources/"
        - "classpath:/static/"
        - "classpath:/public/"
```

## Rest风格请求处理

> Rest风格支持：使用HTTP请求方式动词来表示对资源的操作
> - `GET`：查
> - `POST`：增
> - `DELETE`：删
> - `PUT`：改

- POSTMAN可以用直接发送PUT/DELETE等方式的请求，但是浏览器表单只支持GET/POST方式！

```java
package com.charlie.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController     // @ResponseBody(以JSON格式返回数据，而非进行视图解析) + @Controller
@Controller
public class MonsterController {

    //@RequestMapping(value = "/monster", method = RequestMethod.GET)   // 等价写法
    @GetMapping("/monster")
    public String getMonster() {
        return "GET-查询妖怪";
    }

    @PostMapping("/monster")
    public String saveMonster() {
        return "POST-添加妖怪";
    }

    @PutMapping("/monster")
    public String putMonster() {
        return "PUT-修改妖怪";
    }

    @DeleteMapping("/monster")
    public String delMonster() {
        return "DELETE-删除妖怪";
    }

    @RequestMapping("/go")
    public String go() {
        // 1. (没有配置视图解析器时)先看controller有没有 /hello
        // 2. 如果配置了视图解析器，就去定位资源页面
        return "hello";
    }
}
```

### REST风格请求注意事项和细节

1. 客户端是POSTMAN，可以直接发送Put,Delete等方式的请求，可不设置Filter
2. 如果要SpringBoot支持页面表单Rest功能，则需要注意以下细节
   1) Rest风格请求核心Filter：`HiddenHttpMethodFilter`，表单请求会被其拦截，获取到表单隐藏域`_method`的值
   2) 判断是`PUT/DELETE/PATCH`(PATCH方法是新引入的，对PUT方法的补充，用来对已知资源进行局部更新)
   3) SpringBoot支持页面表单的Rest功能，需要在`application.yaml`中启用Filter功能，否则无效
3. `@RestController`注解会以JSON格式返回数据，而非进行视图解析
4. `@Controller`：默认情况下会进行视图解析
   - 当配置了视图解析器(如下的`view`)时，会根据返回的字符串查询对应的视图页面
   - 当没有配置视图解析器时，会先根据对应的字符串查询是否有对应的Controller与其对应；如果没有，再去匹配静态资源

```yaml
spring:
   mvc:
      static-path-pattern: /charlieRes/**   # 修改静态资源访问的路径/前缀
      hiddenmethod:
         filter:
            enabled: true   # 启用了HiddenHttpMethodFilter，支持页面表单请求Rest风格url
      view:               # 配置视图解析器
         suffix: .html     # 后缀名
         prefix: /charlieRes/  # 前缀名：需要根据配置的静态资源路径前缀进行调整。如果没配置，则使用/
```

## 接收参数相关注解

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>接收请求参数</title>
</head>
<body>
<h1>SpringBoot接收请求参数</h1>
<h2>基本注解：</h2>
<hr/>
<!--
1. href="/monster1/100/king"：其中 / 被解析为 localhost:8080，
    即 http://localhost:8080/monster1/100/king 因此可以访问到@GetMapping(value = "/monster1/{id}/{name}")
2. href="monster1/100/king"：如果配置了静态路径(如static-path-pattern: /charlieRes/**)，
    此时访问index.html路径为 localhost:8080/charlieRes/index.html
    因此，该href会被解析为 localhost:8080/charlieRes/monster1/100/king ，所以是访问不到的
-->
<a href="/monster1/100/king">@PathVariable-路径变量 monster/100/king</a><br/><br/>
<a href="/requestHeader">@RequestHeader-获取Http请求头</a><br/><br/>
<a href="/hello?name=charlie&fruit=apple&fruit=banana&address=北京&id=200">@RequestParam-获取请求参数</a><br/><br/>
<a href="/cookie">@CookieValue-获取cookie值</a><br/><br/>
<a href="/login">@RequestAttribute-获取req域属性</a><br/><br/>
<hr/>
<h2>测试@RequestBody获取数据：获取POST请求体</h2>
<form action="/save" method="post">
    姓名：<input type="test" name="name"/><br/>
    年龄：<input type="test" name="age"/><br/>
    <input type="submit" value="提交"/>
</form>

</body>
</html>
```

```java
package com.charlie.springboot.controller;

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
```

```html
package com.charlie.springboot.controller;

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
```
