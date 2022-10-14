package com.tblog.blog_api.security;


import com.tblog.blog_api.entity.Admin;
import com.tblog.blog_api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这个是实现UserDetailsService类的时候必须实现的抽象接口，作用是定义安全登录的用户加载方法
        //登录的时候，会把username 传递到这里
        //通过username查询 admin表，如果 admin存在 将密码告诉spring security
        //如果不存在 返回null 认证失败了
        Admin admin = this.adminService.findAdminByUsername(username);
        if (admin == null){
            //登录失败
            return null;
        }
        //TODO 查询权限信息
        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));
        //UserDetails userDetails = new User(username,admin.getPassword(),new ArrayList<>());
        //MyUserDetails myUserDetails = new MyUserDetails(admin,new ArrayList<>());
        MyUserDetails myUserDetails = new MyUserDetails(admin,list);
        return myUserDetails;
    }
}
