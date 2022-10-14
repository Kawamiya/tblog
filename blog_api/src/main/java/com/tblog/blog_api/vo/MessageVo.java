package com.tblog.blog_api.vo;

import lombok.Data;

@Data
public class MessageVo {

    private String id;

    private String nickname;

    private String avatar;

    private String content;

    private Long time;
}
