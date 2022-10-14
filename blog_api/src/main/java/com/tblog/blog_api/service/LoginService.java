package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.SysUser;
import com.tblog.blog_api.vo.params.LoginParam;

public interface LoginService {
    /**
     * 登陆功能
     * @param loginParam
     * @return
     */
    com.blog_api.vo.Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    com.blog_api.vo.Result logout(String token);

    com.blog_api.vo.Result register(LoginParam loginParam);
}
