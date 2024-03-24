package com.charlie.springboot.config;

import com.charlie.springboot.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1. @Configuration标识一个配置类
 * 2. 如果该配置类，在springboot扫描的包/子包下，本身会被注入到Spring容器中，name/id 为类名首字母小写
 * 3. 在该类中，可以通过 @Bean 来注入其它的组件
 */
@Configuration
public class Config {

    /**
     * 1. 通过 @Bean 的方式，将 new 出来的 Bean 对象，放入到 Spring 容器
     * 2. 该 bean 在Spring容器中的 name/id 默认就是方法名 dog
     * 3. 通过方法名，可以得到注入到spring容器中的bean
     */
    @Bean
    public Dog dog() {
        return new Dog();
    }
}
