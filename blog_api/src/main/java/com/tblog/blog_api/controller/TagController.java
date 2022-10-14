package com.tblog.blog_api.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tblog.blog_api.entity.Article;
import com.tblog.blog_api.entity.Tag;
import com.tblog.blog_api.service.ArticleTagService;
import com.tblog.blog_api.service.TagService;
import com.tblog.blog_api.vo.ArticleVo;
import com.tblog.blog_api.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.blog_api.vo.Result;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("hot")
    public Result hotTag(){
        int tagNumber_limit=6;
        return tagService.hots(tagNumber_limit);
    }

    @GetMapping
    public Result tagAllList(){
        return tagService.tagAllList();
    }

    @GetMapping("/detail")
    public Result tagAllListDetail(){
        return tagService.tagAllListDetail();
    }

    @GetMapping("/detail/{id}")
    public Result tagAllListDetailById(@PathVariable Long tagId){
        return tagService.tagAllListDetailById(tagId);
    }

}
