package com.tblog.blog_api.controller;

import com.tblog.blog_api.service.LoginService;
import com.tblog.blog_api.service.SysUserService;
import com.tblog.blog_api.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    //    @Autowired
    //    private SysUserService sysUserService;

    @Autowired
    LoginService loginService;


    @PostMapping
    public com.blog_api.vo.Result login(@RequestBody LoginParam loginParam){
        //LoginParam loginParam1 = new LoginParam();
        //loginParam1.setAccount("admin");
        //loginParam1.setPassword("admin");
        return loginService.login(loginParam);
    }
}
