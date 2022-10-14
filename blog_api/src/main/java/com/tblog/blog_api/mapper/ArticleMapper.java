package com.tblog.blog_api.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tblog.blog_api.entity.Archives;
import com.tblog.blog_api.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@Component
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();
    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);
}
