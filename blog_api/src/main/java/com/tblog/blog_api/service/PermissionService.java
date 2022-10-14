package com.tblog.blog_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.entity.Permission;
import com.tblog.blog_api.vo.params.AdminPageParam;
import com.blog_api.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-20
 */
public interface PermissionService extends IService<Permission> {

    com.blog_api.vo.Result listPermission(AdminPageParam pageParam);


    Result addPermission(Permission permission);

    Result updatePermission(Permission permission);

    Result deletePermission(Long id);
}
