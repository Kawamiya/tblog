package com.tblog.blog_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog_api.vo.Result;
import com.tblog.blog_api.entity.Message;
import com.tblog.blog_api.entity.SysUser;
import com.tblog.blog_api.mapper.CommentMapper;
import com.tblog.blog_api.mapper.MessageMapper;
import com.tblog.blog_api.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.service.SysUserService;
import com.tblog.blog_api.vo.ArticleVo;
import com.tblog.blog_api.vo.MessageVo;
import com.tblog.blog_api.vo.TagVo;
import com.tblog.blog_api.vo.params.MessageParam;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2022-03-13
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Result listMessage() {
        List<Message> messageList = this.list();
        return com.blog_api.vo.Result.SuccessResponse(copylist(messageList));
    }

    @Override
    public Result messageCreate(MessageParam messageParam) {
        Message message = new Message();
        message.setAuthorId(messageParam.getAuthorId());
        message.setContent(messageParam.getContent());
        message.setCreateDate(System.currentTimeMillis());
        message.setTime(messageParam.getTime());
        messageMapper.insert(message);
        return Result.SuccessResponse(null);
    }

    private List<MessageVo> copylist(List<Message> messageList) {
        List<MessageVo> messageVoList = new ArrayList<>();
        for (Message message : messageList) {
            messageVoList.add(copy(message));
        }
        return messageVoList;
    }

    private MessageVo copy(Message message) {
        MessageVo messageVo = new MessageVo();
        BeanUtils.copyProperties(message,messageVo);
//        messageVo.setCreateDate(new DateTime(message.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        messageVo.setId(message.getId().toString());

        //MessageVo.setAuthor();
        String authorId = message.getAuthorId();
        if (StringUtils.isNotBlank(authorId)){
            Long authorId_int=Long.valueOf(authorId);
            SysUser sysUser = sysUserService.getById(authorId_int);
            messageVo.setNickname(sysUser.getNickname());
            messageVo.setAvatar(sysUser.getAvatar());
//            messageVo.setAuthor( sysUserService.copy(sysUser));
        }
        return messageVo;
    }

}
