package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.vo.CategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
public interface CategoryService extends IService<Category> {

    CategoryVo copy(Category category);

    com.blog_api.vo.Result categoryAllList();

    List<CategoryVo> copyList(List<Category> categoryList);

    com.blog_api.vo.Result categoryListDetail();

    com.blog_api.vo.Result categoryListDetailById(Long categoryId);
}
