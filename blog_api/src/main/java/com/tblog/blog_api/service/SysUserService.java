package com.tblog.blog_api.service;

import com.tblog.blog_api.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tblog.blog_api.vo.UserVo;
import com.tblog.blog_api.vo.params.PageParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TB
 * @since 2022-02-12
 */
public interface SysUserService extends IService<SysUser> {
    UserVo copy(SysUser sysUser);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    com.blog_api.vo.Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    UserVo findUserVoById(Long id);

    com.blog_api.vo.Result listUsers(PageParam pageParams);
    com.blog_api.vo.Result deleteUserById(Long userId);
}
