package com.tblog.blog_api.controller;


import com.tblog.blog_api.common.aop.LogAnnotation;
import com.tblog.blog_api.common.cache.Cache;
import com.tblog.blog_api.service.ArticleService;
import com.tblog.blog_api.service.ArticleTagService;
import com.tblog.blog_api.service.SysUserService;
import com.tblog.blog_api.service.TagService;
import com.tblog.blog_api.vo.params.ArticleParam;
import com.tblog.blog_api.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.blog_api.vo.Result;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleTagService articleTagService;
    @Autowired
    SysUserService sysUserService;
    //    @PostMapping
//    public Result listBlog(@RequestBody PageParams pageParams){
//        //List<Blog> blog = blogService.list();
//        Page<Blog> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(Blog::getCreated);
//        Page<Blog> blogPage = blogService.page(page,queryWrapper);
//        List<Blog> records = blogPage.getRecords();
//        //不能直接返回（可以用vo对象返回
//        return Result.SuccessResponse(records);
//    }
    //文章列表获取
    @PostMapping("/listArchivesByDate")
    @LogAnnotation(module="文章",operator="获取文章归档")
    @Cache(expire = 5 * 60 * 1000,name = "listArticleByArchives")
    public Result listArticleByArchives(@RequestBody PageParam pageParams){

        return articleService.listArticleByArchives(pageParams);
    }

    @PostMapping
    @LogAnnotation(module="文章",operator="获取文章列表")
//    @Cache(expire = 5 * 60 * 1000,name = "listArticle")
    public Result listArticle(@RequestBody PageParam pageParams){
        //List<Blog> blog = blogService.list();
        return articleService.listArticle(pageParams);
    }

    //最热文章获取
    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    //最新文章获取
    @PostMapping("/new")
    @Cache(expire = 5 * 60 * 1000,name = "new_article")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    //文章归档
    @PostMapping("/listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("/view/{id}")
    @Cache(expire = 5 * 60 * 1000,name = "listview_byId")
    public Result findByArticleId(@PathVariable("id") Long articleId){
        return articleService.findByArticleId(articleId);
    }

    @LogAnnotation(module="文章",operator="创建案例文章")
    @PostMapping("/publish")
    public Result publishArticle(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    @PostMapping("/delete/{id}")
    public Result deleteArticleById(@PathVariable("id") Long articleId){
        return articleService.deleteByArticleId(articleId);
    }

}
