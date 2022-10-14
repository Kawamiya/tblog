package com.tblog.blog_api.vo.params;

import com.tblog.blog_api.vo.CategoryVo;
import com.tblog.blog_api.vo.TagVo;
import com.tblog.blog_api.vo.CategoryVo;
import com.tblog.blog_api.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
