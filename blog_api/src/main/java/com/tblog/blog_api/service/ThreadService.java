package com.tblog.blog_api.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tblog.blog_api.entity.Article;
import com.tblog.blog_api.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    //期望此操作在线程池进行，不会影响原有线程
    @Async("taskExcutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        Integer viewCounts = article.getViewCounts();
        Article articleUpdata = new Article();
        articleUpdata.setViewCounts(viewCounts+1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //设置一个 为了在多线程的环境下，保证线程安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        // update article set view_count=100 where view_count=99 and id=11

        articleMapper.update(articleUpdata,updateWrapper);

        try {
            Thread.sleep(5000);
            System.out.printf("更新完成....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
