package com.tblog.blog_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.entity.Admin;
import com.tblog.blog_api.entity.AdminPermission;
import com.tblog.blog_api.entity.Permission;
import com.tblog.blog_api.mapper.AdminMapper;
import com.tblog.blog_api.service.AdminPermissionService;
import com.tblog.blog_api.service.AdminService;
import com.tblog.blog_api.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TB
 * @since 2022-02-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminPermissionService adminPermissionService;
    @Autowired
    private PermissionService permissionService;
    @Override
    public Admin findAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public List<Permission> findPermissionByAdminId(Long adminId) {
        LambdaQueryWrapper<AdminPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminPermission::getAdminId,adminId);
        AdminPermission adminPermission = adminPermissionService.getOne(queryWrapper);
        Long permissionId = adminPermission.getPermissionId();

        LambdaQueryWrapper<Permission> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        permissionLambdaQueryWrapper.eq(Permission::getId,permissionId);
        List<Permission> permissionList = permissionService.list(permissionLambdaQueryWrapper);
        return permissionList;
    }
}
