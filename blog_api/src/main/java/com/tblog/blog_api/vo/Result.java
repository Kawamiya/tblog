package com.blog_api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private boolean success;
    private int code; //正常：200 异常：400
    private String msg;
    private Object data;

    public static Result SuccessResponse(Object data){
        return SuccessResponse(200,"操作成功",data);
    }

    public static Result SuccessResponse(int code, String msg, Object data){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result FailedResponse(String msg){
        return FailedResponse(400, msg, null);
    }
    public static Result FailedResponse(int code, String msg){
        return FailedResponse(code, msg, null);
    }

    public static Result FailedResponse(int code, String msg, Object data){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
