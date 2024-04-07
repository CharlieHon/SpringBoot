package com.charlie.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice   // 使用它可以表示一个全局异常处理器/对象，会注入到spring容器
@Slf4j
public class GlobalExceptionHandler {
    // 编写方法，处理指定异常，如算术异常和空指针异常，可以指定多个异常
    // Exception e: 表示异常发生后，传递的异常对象
    // Model model: 可以将异常信息，放入到model，并传递给显示页面
    // HandlerMethod handlerMethod: 异常发生的方法
    @ExceptionHandler(value = {ArithmeticException.class, NullPointerException.class, AccessException.class})
    public String handleArithmeticException(Exception e, Model model, HandlerMethod handlerMethod) {
        // public java.lang.String com.charlie.springboot.controller.MyErrorController.err()
        // 在这里可以拿到异常发生的方法，是因为底层反射调用时传过来了HandlerMethods类型参数
        log.info("异常发生的方法={}", handlerMethod.getMethod());
        log.info("异常信息={}", e.getMessage());
        // 这里将发生的异常信息放入到model，可以在错误页面显示
        model.addAttribute("msg", e.getMessage());
        return "/error/global";     // 视图地址
    }
}
