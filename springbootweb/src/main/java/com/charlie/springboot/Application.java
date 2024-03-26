package com.charlie.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        /**
         * 1. WebProperties.java
         *  private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
         *  new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
         * 2. WebMvcProperties.java
         *  this.staticPathPattern = "/**";
         */
        SpringApplication.run(Application.class, args);
    }
}
