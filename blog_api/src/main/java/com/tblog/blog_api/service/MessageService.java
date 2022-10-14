package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.vo.params.MessageParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-03-13
 */
public interface MessageService extends IService<Message> {

    com.blog_api.vo.Result listMessage();

    com.blog_api.vo.Result messageCreate(MessageParam messageParam);
}
