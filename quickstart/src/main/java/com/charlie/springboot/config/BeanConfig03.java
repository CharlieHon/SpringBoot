package com.charlie.springboot.config;

import com.charlie.springboot.bean.Furn;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
// 导入beans.xml，就可以获取到beans.xml中配置的bean
@ImportResource(locations = "classpath:beans.xml")
// 启动对特定类的配置属性支持，加上该注解后，不需要在原Bean上再加@Component注解
// 1. 开启Furn配置绑定功能 2. 把Furn组件自动注册到容器中
//@EnableConfigurationProperties({Furn.class})
public class BeanConfig03 {

}
