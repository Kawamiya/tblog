package com.tblog.blog_api.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public com.blog_api.vo.Result doException(Exception e){
        e.printStackTrace();
        return com.blog_api.vo.Result.FailedResponse(999,"系统异常");
    }
}
