package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.ArticleBody;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.vo.ArticleBodyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
public interface ArticleBodyService extends IService<ArticleBody> {

    ArticleBodyVo copy(ArticleBody articleBody);
}
