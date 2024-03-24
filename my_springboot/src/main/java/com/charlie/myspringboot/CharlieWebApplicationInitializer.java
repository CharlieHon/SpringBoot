package com.charlie.myspringboot;

import com.charlie.myspringboot.config.CharlieConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Initializer：初始化器
 * 1. 创建Spring容器
 * 2. 加载/关联Spring容器的配置，按照注解的方式
 * 3. 完成Spring容器配置的bean的创建、依赖注入
 * 4. 创建前端控制器/分发控制器(DispatcherServlet)，并让其持有Spring容器
 * 5. 当DispatcherServlet持有容器，就可以进行分发映射(SpringMVC底层机制)
 * 6. onStartup 是Tomcat调用，并把ServletContext对象传入
 */
public class CharlieWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("startup...");
        // 加载Spring web application configuration   =>      容器
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        // 在ac中注册 CharlieConfig.class 配置类，即将CharlieConfig类指定为配置类，当容器启动时，会读取并应用这些配置类中定义的配置信息
        ac.register(CharlieConfig.class);
        ac.refresh();   // 完成bean的创建和配置

        // 1. 创建注册非常重要的前端控制器DispatcherServlet
        // 2. 让 DispatcherServlet 持有容器
        // 3. 这样就可以进行映射转发
        DispatcherServlet dispatcherServlet = new DispatcherServlet(ac);
        // 返回了 ServletRegistration.Dynamic对象
        ServletRegistration.Dynamic registration =
                servletContext.addServlet("app", dispatcherServlet);
        // 当Tomcat启动时，加载dispatcherServlet
        registration.setLoadOnStartup(1);
        // 拦截请求，并进行分发处理
        registration.addMapping("/");   // 提示：/ 和 /* 的区别
    }
}
