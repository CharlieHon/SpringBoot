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
