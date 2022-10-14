package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.vo.ArticleVo;
import com.tblog.blog_api.vo.params.ArticleParam;
import com.tblog.blog_api.vo.params.PageParam;
import com.blog_api.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
public interface ArticleService extends IService<Article> {


    Result listArticleByArchives(PageParam pageParams);
    Result listArticle(PageParam pageParams);
    List<ArticleVo> copylist(List<Article> records,boolean isTag, boolean isAuthor);

    ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory);
    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    com.blog_api.vo.Result findByArticleId(Long articleId);

    Result deleteByArticleId(Long articleId);

    com.blog_api.vo.Result publish(ArticleParam articleParam);
}
