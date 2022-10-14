package com.tblog.blog_api.vo.params;

import lombok.Data;

@Data
public class AdminPageParam {
    private int currentPage;

    private int pageSize;

    private String queryString;
}
