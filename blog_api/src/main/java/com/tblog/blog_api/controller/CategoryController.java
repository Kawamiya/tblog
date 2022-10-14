package com.tblog.blog_api.controller;


import com.tblog.blog_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public com.blog_api.vo.Result categoryList(){
        return categoryService.categoryAllList();
    }

    @GetMapping("/detail")
    public com.blog_api.vo.Result categoryListDetail(){
        return categoryService.categoryListDetail();
    }

    @GetMapping("/detail/{id}")
    public com.blog_api.vo.Result categoryListDetailById(@PathVariable("id") Long categoryId){
        return categoryService.categoryListDetailById(categoryId);
    }
}
