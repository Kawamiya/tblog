package com.tblog.blog_api.entity;

import lombok.Data;

@Data
public class Archives {

    private Integer year;

    private Integer month;

    private Long count;
}
