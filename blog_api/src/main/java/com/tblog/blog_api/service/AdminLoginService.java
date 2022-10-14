package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.Admin;
import com.tblog.blog_api.security.MyUserDetails;
import com.tblog.blog_api.vo.params.AdminLoginParam;

public interface AdminLoginService {
    com.blog_api.vo.Result login(AdminLoginParam adminLoginParam);

    public MyUserDetails checkToken(String token);
}
