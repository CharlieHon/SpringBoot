package com.charlie.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * AccessException：自定义异常，java基础
 * @ResponseStatus(value = HttpStatus.FORBIDDEN)：表示发生AccessException异常，通过http协议返回的状态码403
 * 这个状态码和自定义异常的对应关系是由程序员自己定义的
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccessException extends RuntimeException {

    // 显示定义无参构造器
    public AccessException() {}

    public AccessException(String message) {    // message：异常信息
        super(message);
    }
}
