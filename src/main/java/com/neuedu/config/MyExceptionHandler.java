package com.neuedu.config;

import com.neuedu.pojo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class MyExceptionHandler {
    @ExceptionHandler
    public Result ExceptionHandler(Exception ex){
        return new Result(0,"错误",ex.getMessage());
    }
}
