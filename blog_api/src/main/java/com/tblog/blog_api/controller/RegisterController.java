package com.tblog.blog_api.controller;

import com.tblog.blog_api.service.LoginService;
import com.tblog.blog_api.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    LoginService loginService;
    @PostMapping
    public com.blog_api.vo.Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
