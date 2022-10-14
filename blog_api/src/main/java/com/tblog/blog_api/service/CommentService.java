package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.vo.params.CommentParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
public interface CommentService extends IService<Comment> {

    com.blog_api.vo.Result commentlist(Long articleId);

    com.blog_api.vo.Result commentCreate(CommentParam commentParam);
}
