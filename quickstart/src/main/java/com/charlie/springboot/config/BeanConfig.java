package com.charlie.springboot.config;

import com.charlie.springboot.bean.Monster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 1. @Configuration：表示/标识这是一个配置类，等价于之前的beans.xml配置文件
 * 2. 这时就可以通过 @Bean 注解，注入bean对象到容器
 * 3. 当一个类被 @Configuration 修饰时，该类/Bean也会被注入到容器
 */

/** 注意事项和使用细节
 * 1. proxyBeanMethods：代理bean的方法
 * 2. Full(proxyBeanMethods=true)：保证每个@Bean注解的方法调用多次返回的组件都是单例的，即代理方式
 * 3. List(proxyBeanMethods=false)：每个@Bean方法被调用多少次返回的组件都是新创建的，即非代理方式
 * 4. 注意：proxyBeanMethods是在调用 @Bean 方法(如monster01()等)时才生效，因此需要先获取BeanConfig组件，再调用方法
 * 5. 直接通过SpringBoot主程序得到的容器来获取Bean(ioc.getBean("monster01", Monster.class))获取时，该proxyBeanMethods并没有生效
 * 6. 如何选择： 组件依赖必须使用 Full 模式默认。如果不需要组件依赖使用 Lite 模式
 * 7. Lite模式也称为轻量级模式，因为不检测依赖关系，运行速度快
 */
@Configuration(proxyBeanMethods = true)
public class BeanConfig {

    /**
     * 1. @Bean：给容器添加组件，就是 Monster bean
     * 2. monster01()：默认方法名 monster01 作为Bean的名字/id
     * 3. Monster：注入类型，方法的返回类型作为注入Bean的类型
     * 4. new Monster(200, "牛魔王", 500, "牛气冲天")：注入到容器中具体的Bean信息，类似文件配置中的属性值
     * 5. @Bean(name = "monster_nmw")：在配置/注入Bean时指定名字/id为 monster_nmw
     * 6. 默认是单例注入
     * 7. @Scope("prototype")：多例，每获取一次创建一个新的对象
     */
    //@Bean(name = "monster_nmw")
    @Bean
    //@Scope("prototype")
    public Monster monster01() {
        return new Monster(200, "牛魔王", 500, "牛气冲天");
    }
}
