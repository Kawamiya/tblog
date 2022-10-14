package com.tblog.blog_api.controller;


import com.tblog.blog_api.service.CommentService;
import com.tblog.blog_api.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("list/{id}")
    public com.blog_api.vo.Result commentlist(@PathVariable("id") Long articleId){
        return commentService.commentlist(articleId);
    }

    @PostMapping("create")
    public com.blog_api.vo.Result commentCreate(@RequestBody CommentParam commentParam){
        return commentService.commentCreate(commentParam);
    }

}
