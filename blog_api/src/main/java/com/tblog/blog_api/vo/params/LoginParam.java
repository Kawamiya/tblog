package com.tblog.blog_api.vo.params;

import lombok.Data;

@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;

    private String avatar = "/static/img/logo.b3a48c0.png";
}
