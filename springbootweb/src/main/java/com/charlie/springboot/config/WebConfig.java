package com.charlie.springboot.config;

import com.charlie.springboot.bean.Car;
import com.charlie.springboot.bean.Monster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 1. @Configuration：WebConfig是一个配置类
 * 2. proxyBeanMethods = false：使用Lite模式，通过配置类对象调用方法返回的bean都是新创建的(非单例的)
 */
@Configuration(proxyBeanMethods = false)
public class WebConfig {

    // 注入Bean Web
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                /**
                 * 1. 在 addFormatters 方法中，增加一个自定义的转换器
                 * 2. 增加自定义转换器，String -> Car
                 * 3. 增加的自定义转换器会注册到 converters 容器中
                 * 4. converters底层结构是 ConcurrentHashMap 内置有124中转换器(不同JDK版本不同)
                 */
                registry.addConverter(new Converter<String, Car>() {
                    @Override
                    public Car convert(String s) {  // s 就是传入的字符串
                        // 这里加入自定义转换业务代码
                        if (!ObjectUtils.isEmpty(s)) {
                            Car car = new Car();
                            String[] split = s.split(",");
                            car.setName(split[0]);
                            car.setPrice(Double.parseDouble(split[1]));
                            return car;
                        }
                        return null;
                    }
                });

                // 另一种添加自定义converter的方法
                Converter<String, Monster> convert1 = new Converter<String, Monster>() {
                    @Override
                    public Monster convert(String s) {
                        return null;
                    }
                };
                registry.addConverter(convert1);

                Converter<String, Car> convert2 = new Converter<String, Car>() {
                    @Override
                    public Car convert(String s) {
                        return null;
                    }
                };
                registry.addConverter(convert2);
            }
        };
    }
}
