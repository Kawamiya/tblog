package com.tblog.blog_api.controller;

import com.tblog.blog_api.entity.SysUser;
import com.tblog.blog_api.utils.UserThreadLocal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public com.blog_api.vo.Result test(){

        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return com.blog_api.vo.Result.SuccessResponse(null);
    }

    @GetMapping("adminlogin")
    public com.blog_api.vo.Result adminlogin(){
        return com.blog_api.vo.Result.SuccessResponse(200,"后台管理登录",null);
    }
}