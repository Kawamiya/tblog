package com.tblog.blog_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tblog.blog_api.entity.*;
import com.tblog.blog_api.mapper.ArticleMapper;
import com.tblog.blog_api.mapper.ArticleTagMapper;
import com.tblog.blog_api.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.utils.UserThreadLocal;
import com.tblog.blog_api.vo.ArticleVo;
import com.tblog.blog_api.vo.ErrorCode;
import com.tblog.blog_api.vo.TagVo;
import com.tblog.blog_api.vo.params.ArticleParam;
import com.tblog.blog_api.vo.params.PageParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.blog_api.vo.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleTagService articleTagService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    private ArticleBodyService articleBodyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ThreadService threadService;

    @Override
    public Result listArticleByArchives(PageParam pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());

        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        for (Article record : records) {
            String viewCount = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(record.getId()));
            if (viewCount != null){
                record.setViewCounts(Integer.parseInt(viewCount));
            }
        }
        return Result.SuccessResponse(copylist(records,true,true));
    }


    @Override
    public Result listArticle(PageParam pageParams){
        //List<Blog> blog = blogService.list();
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId()!=null){
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId()!=null){
            //article表中并没有tag，因为一篇文章有多个tag
            //需用关联表查询
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagService.list(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleIdList.size()>0){
                //查询条件，id 在这个list里面
                queryWrapper.in(Article::getId,articleIdList);
            }
        }
        queryWrapper.orderByAsc(Article::getId);
        Page<Article> blogPage = articleMapper.selectPage(page,queryWrapper);
        List<Article> records = blogPage.getRecords();
        //考虑是否用vo封装（可以用vo对象返回
        List<ArticleVo> articleVoList = copylist(records,true,true);

        return com.blog_api.vo.Result.SuccessResponse(articleVoList);
    }

    @Override
    public  List<ArticleVo> copylist(List<Article> records,boolean isTag, boolean isAuthor){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    @Override
    public ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        //并不是所有接口都需要 tag 和 author
        articleVo.setId(article.getId().toString());
        if(isTag){
            Long articleId = article.getId();
            //要进行多表查询
            List<ArticleTag> articleTagList = articleTagService.list();
            List<Long> tagIdList = new ArrayList<>();
            for (ArticleTag articleTag : articleTagList) {
                if(articleTag.getArticleId()==articleId){
                    tagIdList.add(articleTag.getTagId());
                }
            }
            List<Tag> tagList = new ArrayList<>();
            for (Long aLong : tagIdList) {
                tagList.add(tagService.getById(aLong));
            }
            List<TagVo> tagVoList = tagService.copylist(tagList);
            articleVo.setTags(tagVoList);
        }
        if(isAuthor){
            //articleVo.setAuthor();
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.getById(authorId);
            articleVo.setAuthor( sysUserService.copy(sysUser));
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            ArticleBody articleBody = articleBodyService.getById(bodyId);
            articleVo.setBody(articleBodyService.copy(articleBody));
        }
        if (isCategory){
            Integer categoryId = article.getCategoryId();
            Category category = categoryService.getById(categoryId);
            articleVo.setCategory(categoryService.copy(category));
        }

        return articleVo;
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.SuccessResponse(copylist(articles,false,false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by create_date desc desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.SuccessResponse(copylist(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.SuccessResponse(archivesList);
    }

    @Override
    public Result findByArticleId(Long articleId) {
        /**
         * 1.根据id查询文章信息
         * 2.根据bodyId和categoryId查询文章
         */
        ;
        Article article = articleMapper.selectById(articleId);
        if(article==null){
            return Result.FailedResponse(ErrorCode.PARAMS_ERROR.getCode(),"未找到对应文章");
        }
        ArticleVo articleVo = copy(article,true,true,true,true);

        //查看完文章以后需要更新阅读数
        //如果直接在这个部分加，查看文章后本应该直接返回数据，，但是这时候如果更新阅读数，会有问题
        //1.会加写锁，阻塞其他操作
        //2.增加耗时
        //如果更新出问题，不能影响我们这个查看文章功能
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.SuccessResponse(articleVo);
    }

    @Override
    public Result deleteByArticleId(Long articleId) {
        /**
         * 1.根据id删除文章
         */
        articleMapper.deleteById(articleId);
        articleBodyService.removeById(articleId);
        return Result.SuccessResponse(null);
    }


    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1.发布文章，构建Article对线
         * 2.作者id 当前登录用户
         * 3.标签 要加入到关联列表当中
         * 4.body 内容存储 article bodyId
         */
        SysUser sysUser = UserThreadLocal.get();
        //需要登录拦截器认证
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Integer.valueOf(articleParam.getCategory().getId()));
        //插入之后会生成一个文章ID
        this.articleMapper.insert(article);
        Long articleId = article.getId();
        //tag.
        List<TagVo> tags = articleParam.getTags();
        if(tags!=null){
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(Long.valueOf(tag.getId()));
                articleTagService.save(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyService.save(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.SuccessResponse(map);
    }

}