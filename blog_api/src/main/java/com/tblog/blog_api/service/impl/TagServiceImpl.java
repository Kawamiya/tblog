package com.tblog.blog_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tblog.blog_api.entity.Tag;
import com.tblog.blog_api.mapper.TagMapper;
import com.tblog.blog_api.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blog_api.vo.Result;

import java.util.ArrayList;
import java.util.Collections;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

    @Override
    public List<TagVo> copylist(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public Result hots(int limit) {
        /**
         * 1. 标签所拥有的文章数量最多 最热标签
         * 2. 查询 根据tag_id 分组 计数，从大到小 排列 取前 limit个
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)){
            return Result.SuccessResponse(Collections.emptyList());
        }
        //需求的是 tagId 和 tagName  Tag对象
        //select * from tag where id in (1,2,3,4)
        List<Tag> tagList = new ArrayList<>();
        for (Long tagId : tagIds) {
            tagList.add(tagMapper.selectById(tagId));
        }
        return Result.SuccessResponse(tagList);
    }

    @Override
    public Result tagAllList() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.SuccessResponse(copylist(tags));
    }

    @Override
    public Result tagAllListDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.SuccessResponse(copylist(tags));
    }

    @Override
    public Result tagAllListDetailById(Long tagId) {
        Tag tag = tagMapper.selectById(tagId);
        return Result.SuccessResponse(copy(tag));
    }
}
