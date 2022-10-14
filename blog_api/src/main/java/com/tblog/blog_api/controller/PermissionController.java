package com.tblog.blog_api.controller;


import com.tblog.blog_api.entity.Permission;
import com.tblog.blog_api.service.PermissionService;
import com.tblog.blog_api.vo.params.AdminPageParam;
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
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permissionList")
    public com.blog_api.vo.Result listPermission(@RequestBody AdminPageParam pageParam){
        return permissionService.listPermission(pageParam);
    }
    @PostMapping("/add")
    public com.blog_api.vo.Result addPermission(@RequestBody Permission permission){
        return permissionService.addPermission(permission);
    }

    @PostMapping("/update")
    public com.blog_api.vo.Result updatePermission(@RequestBody Permission permission){
        return permissionService.updatePermission(permission);
    }

    @GetMapping("/delete/{id}")
    public com.blog_api.vo.Result deletePermission(@PathVariable("id") Long id){
        return permissionService.deletePermission(id);
    }
}
