package com.tblog.blog_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.blog_api.vo.Result;
import com.tblog.blog_api.entity.Comment;
import com.tblog.blog_api.entity.SysUser;
import com.tblog.blog_api.mapper.CommentMapper;
import com.tblog.blog_api.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.service.SysUserService;
import com.tblog.blog_api.utils.UserThreadLocal;
import com.tblog.blog_api.vo.CommentVo;
import com.tblog.blog_api.vo.UserVo;
import com.tblog.blog_api.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentlist(Long articleId){
        /**
         * 最终返回一个CommentVo
         * 1.根据文章id查询评论列表，从comment表查询
         * 2.根据作者id查询作者信息
         * 3.判断是否有子评论(level是否为1 代表是否为一级评论)，如果有->根据评论id进行子评论查询
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copylist(comments);
        return Result.SuccessResponse(commentVoList);
    }

    @Override
    public Result commentCreate(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setAuthorId(sysUser.getId());
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel("1");
        }else{
            comment.setLevel("2");
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.SuccessResponse(null);
    }


    private List<CommentVo> copylist(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        commentVo.setId(comment.getId().toString());
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVoById = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVoById);
        //level信息
        commentVo.setLevel(Integer.valueOf(comment.getLevel()));
        //子评论
        String level = comment.getLevel();
        if (Integer.valueOf(level)==1){
            Long commentId = comment.getId();
            List<CommentVo> commentList = findCommentsByParentsId(commentId);
            commentVo.setChildrens(commentList);
        }
        //toUser信息 给谁评论
        if (Integer.valueOf(level)>1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentsId(Long parentsId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,parentsId);
        queryWrapper.eq(Comment::getLevel,2);
        //level为2 说明是子评论
        return copylist(commentMapper.selectList(queryWrapper));
    }

}
