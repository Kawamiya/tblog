package com.tblog.blog_api.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tblog.blog_api.common.aop.LogAnnotation;
import com.tblog.blog_api.entity.Article;
import com.tblog.blog_api.entity.ArticleTag;
import com.tblog.blog_api.service.SysUserService;
import com.tblog.blog_api.vo.ArticleVo;
import com.tblog.blog_api.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    SysUserService sysUserService;
    @GetMapping("/currentUser")
    public com.blog_api.vo.Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }

    @PostMapping("/list")
//    @Cache(expire = 5 * 60 * 1000,name = "listArticle")
    public com.blog_api.vo.Result listArticle(@RequestBody PageParam pageParams){
        //List<Blog> blog = blogService.list();
        return sysUserService.listUsers(pageParams);
    }
    @PostMapping("/delete/{id}")
    public com.blog_api.vo.Result deleteUserById(@PathVariable("id") Long userId){
        return sysUserService.deleteUserById(userId);
    }


}
