package com.tblog.blog_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.entity.Permission;
import com.tblog.blog_api.mapper.PermissionMapper;
import com.tblog.blog_api.vo.params.AdminPageParam;
import com.tblog.blog_api.service.PermissionService;
import com.tblog.blog_api.vo.PageResult;
import com.blog_api.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TB
 * @since 2022-02-20
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result listPermission(AdminPageParam pageParam) {
        /**
         * 要的数据： 管理台 表的所有字段 ，最终是一个Permission对象
         * 进行分页查询
         */
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(pageParam.getQueryString()) ){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.SuccessResponse(pageResult);
    }

    @Override
    public Result addPermission(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.SuccessResponse(null);
    }

    @Override
    public Result updatePermission(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.SuccessResponse(null);
    }

    @Override
    public Result deletePermission(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.SuccessResponse(null);
    }
}
