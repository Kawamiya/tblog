package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.vo.ArticleVo;
import com.tblog.blog_api.vo.TagVo;

import java.util.List;
import com.blog_api.vo.Result;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
public interface TagService extends IService<Tag> {

    TagVo copy(Tag tag);
    List<TagVo> copylist(List<Tag> tagList);
    Result hots(int limit);

    com.blog_api.vo.Result tagAllList();

    com.blog_api.vo.Result tagAllListDetail();

    com.blog_api.vo.Result tagAllListDetailById(Long tagId);
}
