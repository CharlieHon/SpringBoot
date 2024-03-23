package com.charlie.springboot.config;

import com.charlie.springboot.bean.Cat;
import com.charlie.springboot.bean.Dog;
import com.charlie.springboot.bean.Monster;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 1. @Import源码如下，可以指定 class 的数组，可以注入指定类型的Bean
         * public @interface Import {
         *     Class<?>[] value();
         * }
 * 2. 通过@Import方式注入的组件，默认组件名字/id就是对应类型的全类名，
 *          如Dog类型 com.charlie.springboot.bean.Dog
 */
@Import(value = {Dog.class, Cat.class})
@Configuration
public class BeanConfig2 {

    @Bean
    public Monster monster02() {
        return new Monster(800, "蜘蛛精", 80, "盘丝洞");
    }

    @Bean
    /**
     * 1. @ConditionalOnBean(name = "monster_nmw") 表示
     * 2. 当容器中有一个Bean，名字是monster_nmw时，就注入dog01这个Dog的bean
     * 3. 如果没有，就不进行注入
     * 4. 该注解只对Bean的名字进行约束，并不检测其类型，即只要有名为monster_nmw的bean就可以
     * 5. 还有很多其它的条件约束注解，如 @ConditionalOnMissingBean(name="monster_nmw")表示在容器中没有名字/id为的monster_nmw，才进行注入
     * 6. @ConditionalOnBean 注解可以加载BeanConfig配置类上，表示该类下所有要注入的组件都需要满足约束条件才进行注入
     */
    @ConditionalOnBean(name = "monster_nmw")
    public Dog dog01() {
        return new Dog();
    }
}
