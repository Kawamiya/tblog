package com.tblog.blog_api.vo;

import lombok.Data;

@Data
public class AdminUserVo {
    private String id;

    /**
     * 账号
     */
    private String account;


    /**
     * 注册时间
     */
    private String createDate;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    private String lastLogin;

    /**
     * 手机号
     */
    private String mobilePhoneNumber;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 状态
     */
    private String status;
}
