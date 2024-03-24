package com.charlie.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        // 问题：当执行run方法时，怎么就启动了内置的tomcat
        // 在分析run方法的磁层机制的基础商，尝试实现
        ConfigurableApplicationContext ioc = SpringApplication.run(MainApp.class, args);

        /**
         * Debug SpringApplication.run(MainApp.class, arg);
         * 1. SpringApplication.java
         *     public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
         *         return run(new Class[]{primarySource}, args);
         *     }
         *
         * 2. SpringApplication.java：创建返回 ConfigurableApplicationContext 对象
         *     public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
         *         return (new SpringApplication(primarySources)).run(args);
         *     }
         *
         * 3. SpringApplication.java
         * public ConfigurableApplicationContext run(String... args) {
         *         StopWatch stopWatch = new StopWatch();
         *         stopWatch.start();
         *         DefaultBootstrapContext bootstrapContext = this.createBootstrapContext();
         *         ConfigurableApplicationContext context = null;
         *         this.configureHeadlessProperty();
         *         SpringApplicationRunListeners listeners = this.getRunListeners(args);
         *         listeners.starting(bootstrapContext, this.mainApplicationClass);
         *
         *         try {
         *             ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
         *             ConfigurableEnvironment environment = this.prepareEnvironment(listeners, bootstrapContext, applicationArguments);
         *             this.configureIgnoreBeanInfo(environment);
         *             Banner printedBanner = this.printBanner(environment);
         *             // TODO 重点分析：创建容器
         *             context = this.createApplicationContext();
         *             context.setApplicationStartup(this.applicationStartup);
         *             this.prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
         *             // TODO 重点分析：刷新应用程序上下文，比如 初始化默认设置、注入相关bean、启动tomcat
         *             this.refreshContext(context);
         *             this.afterRefresh(context, applicationArguments);
         *             stopWatch.stop();
         *             if (this.logStartupInfo) {
         *                 (new StartupInfoLogger(this.mainApplicationClass)).logStarted(this.getApplicationLog(), stopWatch);
         *             }
         *
         *             listeners.started(context);
         *             this.callRunners(context, applicationArguments);
         *         } catch (Throwable var10) {
         *             this.handleRunFailure(context, var10, listeners);
         *             throw new IllegalStateException(var10);
         *         }
         *
         *         try {
         *             listeners.running(context);
         *             return context;
         *         } catch (Throwable var9) {
         *             this.handleRunFailure(context, var9, (SpringApplicationRunListeners)null);
         *             throw new IllegalStateException(var9);
         *         }
         *     }
         *
         * 4. SpringApplication.java：容器类型很多，会根据 this.webApplicationType 类型创建对象
         * 默认是 SERVLET 也就是 web容器
         *     protected ConfigurableApplicationContext createApplicationContext() {
         *         return this.applicationContextFactory.create(this.webApplicationType);
         *     }
         *
         * 5. ApplicationContextFactory.class
         *     ApplicationContextFactory DEFAULT = (webApplicationType) -> {
         *         try {
         *             switch (webApplicationType) {
         *                 // 默认是进入到这个分支，返回AnnotationConfigServletWebServerApplicationContext类型
         *                 case SERVLET:
         *                     return new AnnotationConfigServletWebServerApplicationContext();
         *                 case REACTIVE:
         *                     return new AnnotationConfigReactiveWebServerApplicationContext();
         *                 default:
         *                     return new AnnotationConfigApplicationContext();
         *             }
         *         } catch (Exception var2) {
         *             throw new IllegalStateException("Unable create a default ApplicationContext instance, you may need a custom ApplicationContextFactory", var2);
         *         }
         *     };
         *
         * 6. SprintApplication.java
         *     private void refreshContext(ConfigurableApplicationContext context) {
         *         if (this.registerShutdownHook) {
         *             shutdownHook.registerApplicationContext(context);
         *         }
         *
         *         this.refresh(context);   // 重点：真正执行相关任务
         *     }
         *
         * 7. SprintApplication.java
         *     protected void refresh(ConfigurableApplicationContext applicationContext) {
         *         applicationContext.refresh();
         *     }
         *
         * 8. ServletWebServerApplicationContext.java
         *     public final void refresh() throws BeansException, IllegalStateException {
         *         try {
         *             super.refresh();     // 分析这个方法
         *         } catch (RuntimeException var3) {
         *             WebServer webServer = this.webServer;
         *             if (webServer != null) {
         *                 webServer.stop();
         *             }
         *             throw var3;
         *         }
         *     }
         *
         * 9. AbstractApplicationContext.java
         * public void refresh() throws BeansException, IllegalStateException {
         *         synchronized(this.startupShutdownMonitor) {
         *             StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");
         *             this.prepareRefresh();
         *             ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
         *             this.prepareBeanFactory(beanFactory);
         *
         *             try {
         *                 this.postProcessBeanFactory(beanFactory);
         *                 StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
         *                 this.invokeBeanFactoryPostProcessors(beanFactory);
         *                 this.registerBeanPostProcessors(beanFactory);
         *                 beanPostProcess.end();
         *                 this.initMessageSource();
         *                 this.initApplicationEventMulticaster();
         *                 // 重点分析：当父类完成了通用工作后，再通过动态绑定回到子类
         *                 this.onRefresh();
         *                 this.registerListeners();
         *                 this.finishBeanFactoryInitialization(beanFactory);
         *                 this.finishRefresh();
         *             } catch (BeansException var10) {
         *                 if (this.logger.isWarnEnabled()) {
         *                     this.logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + var10);
         *                 }
         *
         *                 this.destroyBeans();
         *                 this.cancelRefresh(var10);
         *                 throw var10;
         *             } finally {
         *                 this.resetCommonCaches();
         *                 contextRefresh.end();
         *             }
         *
         *         }
         *     }
         *
         * 10. ServletWebServerApplicationContext.class
         *     protected void onRefresh() {
         *         super.onRefresh();
         *         try {
         *             // 重点分析：创建webServer，可以理解成会创建指定web服务——Tomcat
         *             this.createWebServer();
         *         } catch (Throwable var2) {
         *             throw new ApplicationContextException("Unable to start web server", var2);
         *         }
         *     }
         *
         * 11. ServletWebServerApplicationContext.class
         *     private void createWebServer() {
         *         WebServer webServer = this.webServer;
         *         ServletContext servletContext = this.getServletContext();
         *         if (webServer == null && servletContext == null) {
         *             StartupStep createWebServer = this.getApplicationStartup().start("spring.boot.webserver.create");
         *             ServletWebServerFactory factory = this.getWebServerFactory();
         *             createWebServer.tag("factory", factory.getClass().toString());
         *             // 重点分析：使用 TomcatServletWebServerFactory 创建一个Tomcat的WebServer
         *             this.webServer = factory.getWebServer(new ServletContextInitializer[]{this.getSelfInitializer()});
         *             createWebServer.end();
         *             this.getBeanFactory().registerSingleton("webServerGracefulShutdown", new WebServerGracefulShutdownLifecycle(this.webServer));
         *             this.getBeanFactory().registerSingleton("webServerStartStop", new WebServerStartStopLifecycle(this, this.webServer));
         *         } else if (servletContext != null) {
         *             try {
         *                 this.getSelfInitializer().onStartup(servletContext);
         *             } catch (ServletException var5) {
         *                 throw new ApplicationContextException("Cannot initialize servlet context", var5);
         *             }
         *         }
         *
         *         this.initPropertySources();
         *     }
         *
         * 12. TomcatServletWebServerFactory.class：创建Tomcat，并启动
         *     public WebServer getWebServer(ServletContextInitializer... initializers) {
         *         if (this.disableMBeanRegistry) {
         *             Registry.disableRegistry();
         *         }
         *
         *         // 创建Tomcat对象
         *         Tomcat tomcat = new Tomcat();
         *         File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
         *         // 设置相关属性
         *         tomcat.setBaseDir(baseDir.getAbsolutePath());
         *         Connector connector = new Connector(this.protocol);
         *         connector.setThrowOnFailure(true);
         *         tomcat.getService().addConnector(connector);
         *         this.customizeConnector(connector);
         *         tomcat.setConnector(connector);
         *         tomcat.getHost().setAutoDeploy(false);
         *         this.configureEngine(tomcat.getEngine());
         *         Iterator var5 = this.additionalTomcatConnectors.iterator();
         *
         *         while(var5.hasNext()) {
         *             Connector additionalConnector = (Connector)var5.next();
         *             tomcat.getService().addConnector(additionalConnector);
         *         }
         *
         *         this.prepareContext(tomcat.getHost(), initializers);
         *         // 重点分析
         *         return this.getTomcatWebServer(tomcat);
         *     }
         *
         * 13. TomcatServletWebServerFactory.class：这里进行了校验，创建TomcatWebServer
         *     protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
         *         return new TomcatWebServer(tomcat, this.getPort() >= 0, this.getShutdown());
         *     }
         *
         * 14. TomcatServletWebServerFactory.class：
         *     public TomcatWebServer(Tomcat tomcat, boolean autoStart, Shutdown shutdown) {
         *         this.monitor = new Object();
         *         this.serviceConnectors = new HashMap();
         *         Assert.notNull(tomcat, "Tomcat Server must not be null");
         *         this.tomcat = tomcat;
         *         this.autoStart = autoStart;
         *         this.gracefulShutdown = shutdown == Shutdown.GRACEFUL ? new GracefulShutdown(tomcat) : null;
         *         // 进入这个方法
         *         this.initialize();
         *     }
         *
         * 15. TomcatServletWebServerFactory.class：
         *     private void initialize() throws WebServerException {
         *         logger.info("Tomcat initialized with port(s): " + this.getPortsDescription(false));
         *         synchronized(this.monitor) {
         *             try {
         *                 this.addInstanceIdToEngineName();
         *                 Context context = this.findContext();
         *                 context.addLifecycleListener((event) -> {
         *                     if (context.equals(event.getSource()) && "start".equals(event.getType())) {
         *                         this.removeServiceConnectors();
         *                     }
         *
         *                 });
         *                 // 启动Tomcat
         *                 this.tomcat.start();
         *                 this.rethrowDeferredStartupExceptions();
         *
         *                 try {
         *                     ContextBindings.bindClassLoader(context, context.getNamingToken(), this.getClass().getClassLoader());
         *                 } catch (NamingException var5) {
         *                 }
         *
         *                 this.startDaemonAwaitThread();
         *             } catch (Exception var6) {
         *                 this.stopSilently();
         *                 this.destroySilently();
         *                 throw new WebServerException("Unable to start embedded Tomcat", var6);
         *             }
         *
         *         }
         *     }
         */
        System.out.println("Hello, IOC!");
    }
}
