package com.tblog.blog_api.vo.params;

import lombok.Data;

@Data
public class MessageParam {

    private String authorId;

    private String content;

    private Long time;

}
