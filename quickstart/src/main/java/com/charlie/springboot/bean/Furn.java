package com.charlie.springboot.bean;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "furn01")     // 配置属性是通过setter方法设置的，因此需要这些方法
@ToString   // 在编译时，生成toString方法
/**
 * 1. Equivalent to {@code @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode}.
 * 2. @Data 注解等价于使用了如下注解 @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
 * 3. @RequiredArgsConstructor
 */
//@Data
/**
 * 1. 在编译时，会生成无参构造器
 * 2. 当有其它构造器生成时，默认生成的无参构造器会被覆盖掉。如果仍希望有无参构造器，就需要使用 @NoArgsConstructor
 */
@NoArgsConstructor
@AllArgsConstructor     // 在编译时，会生成全参构造器
@Getter         // 前端返回json数据是通过getter获取的属性，如果没有的话，前端拿不到数据。xx.id底层是xx.getId()
@Setter         // 配置属性需要，如果没有的话，所有字段都为null
public class Furn {
    private Integer id;
    private String name;
    private Double price;
}
