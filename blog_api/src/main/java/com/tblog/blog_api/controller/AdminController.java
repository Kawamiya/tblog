package com.tblog.blog_api.controller;


import com.tblog.blog_api.entity.Permission;
import com.tblog.blog_api.service.AdminLoginService;
import com.tblog.blog_api.vo.params.AdminLoginParam;
import com.tblog.blog_api.vo.params.AdminPageParam;
import com.tblog.blog_api.service.PermissionService;
import com.blog_api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TB
 * @since 2022-02-20
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/login")
    public Result login(@RequestBody AdminLoginParam adminLoginParam){
        return adminLoginService.login(adminLoginParam);
    }
//    @Autowired
//    private PermissionService permissionService;
//
//    @PostMapping("/permission/permissionList")
//    public com.blog_api.vo.Result listPermission(@RequestBody AdminPageParam pageParam){
//        return permissionService.listPermission(pageParam);
//    }
//    @PostMapping("permission/add")
//    public com.blog_api.vo.Result addPermission(@RequestBody Permission permission){
//        return permissionService.addPermission(permission);
//    }
//
//    @PostMapping("permission/update")
//    public com.blog_api.vo.Result updatePermission(@RequestBody Permission permission){
//        return permissionService.updatePermission(permission);
//    }
//
//    @GetMapping("permission/delete/{id}")
//    public com.blog_api.vo.Result deletePermission(@PathVariable("id") Long id){
//        return permissionService.deletePermission(id);
//    }

}
