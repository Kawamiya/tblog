package com.tblog.blog_api.mapper;

import com.tblog.blog_api.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
@Component
public interface TagMapper extends BaseMapper<Tag> {
//    根据文章ID查询标签列表
    List<Long> findHotsTagIds(int limit);
}
