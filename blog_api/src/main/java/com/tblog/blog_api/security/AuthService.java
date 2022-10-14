package com.tblog.blog_api.security;

import com.tblog.blog_api.entity.Admin;
import com.tblog.blog_api.entity.Permission;
import com.tblog.blog_api.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        //权限认证
        //请求路径
        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();//这个就是UserDetail信息
        //检测是否登录
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        //检测登录用户权限
        MyUserDetails myUserDetails = (MyUserDetails) principal;
        String username = myUserDetails.getUsername();
        //Admin admin = adminService.findAdminByUsername(username);
        Admin admin = myUserDetails.getAdmin();
        if (admin == null){
            return  false;
        }
        if (1 == admin.getId()){
            //超级管理员
            return true;
        }
        Long id = admin.getId();
        List<Permission> permissionList = this.adminService.findPermissionByAdminId(id);
        requestURI = StringUtils.split(requestURI,'?')[0];
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }
}
