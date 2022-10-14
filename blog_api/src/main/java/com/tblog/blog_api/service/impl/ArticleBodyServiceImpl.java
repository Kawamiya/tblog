package com.tblog.blog_api.service.impl;

import com.tblog.blog_api.entity.ArticleBody;
import com.tblog.blog_api.mapper.ArticleBodyMapper;
import com.tblog.blog_api.service.ArticleBodyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.vo.ArticleBodyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody> implements ArticleBodyService {

    @Override
    public ArticleBodyVo copy(ArticleBody articleBody) {
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody,articleBodyVo);
        return articleBodyVo;
    }
}
