package com.tblog.blog_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.entity.Admin;
import com.tblog.blog_api.entity.Permission;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-20
 */
public interface AdminService extends IService<Admin> {

    Admin findAdminByUsername(String username);

    List<Permission> findPermissionByAdminId(Long id);
}
