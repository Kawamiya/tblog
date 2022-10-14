package com.tblog.blog_api.utils;

import com.tblog.blog_api.entity.SysUser;

public class UserThreadLocal {

    private UserThreadLocal(){}
    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static void remove(){
        LOCAL.remove();
    }

    public static SysUser get(){
        return LOCAL.get();
    }


}
