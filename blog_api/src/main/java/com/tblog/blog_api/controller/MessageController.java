package com.tblog.blog_api.controller;


import com.tblog.blog_api.common.aop.LogAnnotation;
import com.tblog.blog_api.common.cache.Cache;
import com.tblog.blog_api.entity.Article;
import com.tblog.blog_api.service.MessageService;
import com.tblog.blog_api.vo.ArticleVo;
import com.tblog.blog_api.vo.params.CommentParam;
import com.tblog.blog_api.vo.params.MessageParam;
import com.tblog.blog_api.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TB
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/list")
    public com.blog_api.vo.Result listMessage(){
        return messageService.listMessage();

    }

    @PostMapping("create")
    public com.blog_api.vo.Result messageCreate(@RequestBody MessageParam messageParam){
        return messageService.messageCreate(messageParam);
    }

}
