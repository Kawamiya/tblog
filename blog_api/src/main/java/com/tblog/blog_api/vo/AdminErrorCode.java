package com.tblog.blog_api.vo;

public enum AdminErrorCode {
//    PARAMS_ERROR(10001,"参数有误"),
//    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
//    TOKEN_ERROR(10003,"token不合法"),
//    ACCOUNT_EXIST(10004,"账号已存在"),
//    NO_PERMISSION(70001,"无访问权限"),
//    SESSION_TIME_OUT(90001,"会话超时"),
//    FILE_UPLOAD_ERROR(20001,"上传失败"),
//    NO_LOGIN(90002,"未登录"),;

    USER_NOT_LOGIN(2001,"用户未登录"),
    USER_ACCOUNT_EXPIRED(2002,"账号过期"),
    USER_CREDENTIALS_ERROR(2003,"密码错误"),
    USER_CREDENTIALS_EXPIRED(2004,"密码过期"),
    USER_ACCOUNT_DISABLE(2005,"账号不可用"),
    USER_ACCOUNT_LOCKED(2006,"账号锁定"),
    USER_ACCOUNT_NOT_EXIST(2007,"账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008,"账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009,"登录已超时或已经在另一台机器登录"),

    NO_PERMISSION(4001,"没有访问权限"),

    I_DONT_KNOW(400,"我也不知道出什么错了")
    ;

    private int code;
    private String msg;

    AdminErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
