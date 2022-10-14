package com.tblog.blog_api.controller;

import com.tblog.blog_api.service.LoginService;

import com.tblog.blog_api.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public com.blog_api.vo.Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
