package com.charlie.springboot;

import com.charlie.springboot.bean.A;
import com.charlie.springboot.bean.Cat;
import com.charlie.springboot.bean.Dog;
import com.charlie.springboot.bean.Monster;
import com.charlie.springboot.config.BeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @SpringBootApplication：标识这是一个SpringBoot应用
 *      - String[] scanBasePackages() default {};
 *      - scanBasePackages = {"com.charlie"} 指定SpringBoot要扫描的包及其子包，可以指定多个包
 */
@SpringBootApplication(scanBasePackages = {"com.charlie"})
public class MainApp {
    public static void main(String[] args) {
        // 启动SpringBoot应用程序/项目
        ConfigurableApplicationContext ioc = SpringApplication.run(MainApp.class, args);

        /** 如何查看容器中注入的组件 **/
        //String[] beanDefinitionNames = ioc.getBeanDefinitionNames();
        //for (String beanDefinitionName : beanDefinitionNames) {
        //    System.out.println("beanDefinitionName=" + beanDefinitionName);
        //}

        /** 演示Spring中传统的注解依然可以使用 @Controller @Service @Repository @Component **/
        //A bean = ioc.getBean(A.class);
        //// aBean=com.charlie.springboot.bean.A@4f449e8f
        //System.out.println("aBean=" + bean);

        /** 测试SpringBoot中，依然可以使用Spring的配置/注入/获取bean方式 **/
        //ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        //Monster monster = ac.getBean("monster", Monster.class);
        //// monster=Monster{id=1000, name='牛魔王', age=5360, skill='芭蕉扇'}
        //System.out.println("monster=" + monster);

        /** 演示 @Configuration 注解 **/
        // 从BeanConfig配置类/容器获取bean实例
        //Monster monster01 = ioc.getBean("monster01", Monster.class);
        //Monster monster02 = ioc.getBean("monster01", Monster.class);
        //// monster01: Monster{id=200, name='牛魔王', age=500, skill='牛气冲天'} 838800272
        //System.out.println("monster01: " + monster01 + " " + monster01.hashCode());
        //// monster02: Monster{id=200, name='牛魔王', age=500, skill='牛气冲天'} 838800272
        //System.out.println("monster02: " + monster02 + " " + monster02.hashCode());

        /** 演示配置类也会被注入到容器中 **/
        //BeanConfig bean = ioc.getBean(BeanConfig.class);
        //// bean=com.charlie.springboot.config.BeanConfig$$EnhancerBySpringCGLIB$$e1c9df@4f449e8f
        //System.out.println("bean=" + bean);

        /** 演示 @Configuration(proxyBeanMethods = false) **/
        // 1. 先得到BeanConfig组件
        // BeanConfig beanConfig = ioc.getBean(BeanConfig.class);
        //Monster monster_01 = beanConfig.monster01();
        //Monster monster_02 = beanConfig.monster01();
        //// 当 @Configuration(proxyBeanMethods = true)：单例，返回对象hashCode相同
        //// 当 @Configuration(proxyBeanMethods = false)：每次返回的是一个新的对象，非代理方式
        //System.out.println("monster_01=" + monster_01 + " " + monster_01.hashCode());
        //System.out.println("monster_02=" + monster_02 + " " + monster_02.hashCode());

        /** 直接通过ioc.getBean获取时，proxyBeanMethods 不生效 **/
        // monster01和monster02是同一个对象
        // 对于@Bean直接注入到容器的bean对象，其是否是单例由方法上的注解@Scope决定
        //Monster monster01 = ioc.getBean("monster01", Monster.class);
        //Monster monster02 = ioc.getBean("monster01", Monster.class);
        //System.out.println("monster01=" + monster01 + " " + monster01.hashCode());
        //System.out.println("monster02=" + monster02 + " " + monster02.hashCode());

        /** 测试：可以有多个配置类 **/
        //Monster monster02 = ioc.getBean("monster02", Monster.class);
        //// monster02=Monster{id=800, name='蜘蛛精', age=80, skill='盘丝洞'}
        //System.out.println("monster02=" + monster02);

        /** 测试@Import使用 **/
        //Dog dogBean = ioc.getBean(Dog.class);
        //Cat catBean = ioc.getBean(Cat.class);
        //System.out.println("dogBean=" + dogBean);
        //System.out.println("catBean=" + catBean);

        /** 演示 @ConditionalOnBean 的使用 **/
        //Dog dog01 = ioc.getBean("dog01", Dog.class);
        //System.out.println("dog01=" + dog01);

        /** 演示 @ImportResource 注解的使用 **/
        //Monster monster = ioc.getBean("monster3306", Monster.class);
        System.out.println("ioc容器中是否有monster3306组件：" + ioc.containsBean("monster3306"));
    }
}
