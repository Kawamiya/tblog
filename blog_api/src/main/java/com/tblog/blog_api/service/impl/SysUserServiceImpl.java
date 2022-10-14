package com.tblog.blog_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog_api.vo.Result;
import com.tblog.blog_api.entity.*;
import com.tblog.blog_api.mapper.SysUserMapper;
import com.tblog.blog_api.service.LoginService;
import com.tblog.blog_api.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tblog.blog_api.vo.*;
import com.tblog.blog_api.vo.params.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;
    @Override
    public UserVo copy(SysUser sysUser) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(sysUser,userVo);
            userVo.setId(sysUser.getId().toString());
            return userVo;
        }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性校验
         * 是否为空，解析是否成功，redis是否存在
         * 2.如果失败，返回错误
         * 3.如果成功，返回查到的用户Vo结果
         */

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser==null){
            return Result.FailedResponse(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.SuccessResponse(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser==null){
            sysUser = new SysUser();
            sysUser.setNickname("无名");
        }
        UserVo userVo = copy(sysUser);

        return userVo;
    }

    @Override
    public Result listUsers(PageParam pageParams) {
        //List<Blog> blog = blogService.list();
        Page<SysUser> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByAsc(SysUser::getCreateDate);
        Page<SysUser> UserPage = sysUserMapper.selectPage(page,queryWrapper);
        List<SysUser> records = UserPage.getRecords();
        //考虑是否用vo封装（可以用vo对象返回
        List<AdminUserVo> adminUserVoList = copyAdminUserVolist(records);

        return com.blog_api.vo.Result.SuccessResponse(adminUserVoList);
    }


    private List<AdminUserVo> copyAdminUserVolist(List<SysUser> records){
        List<AdminUserVo> adminUserVoList = new ArrayList<>();
        for (SysUser record : records) {
            adminUserVoList.add(copyAdminUserVo(record));
        }
        return adminUserVoList;
    }

    private AdminUserVo copyAdminUserVo(SysUser sysUser){
        AdminUserVo adminUserVo = new AdminUserVo();
        BeanUtils.copyProperties(sysUser,adminUserVo);
        adminUserVo.setCreateDate(new DateTime(sysUser.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        adminUserVo.setLastLogin(new DateTime(sysUser.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        //并不是所有接口都需要 tag 和 author
        adminUserVo.setId(sysUser.getId().toString());

        return adminUserVo;
    }

    @Override
    public Result deleteUserById(Long userId) {
        /**
         * 1.根据id删除用户
         */
        if(userId==1) return Result.FailedResponse("不能删除管理员");
        sysUserMapper.deleteById(userId);
        return Result.SuccessResponse(null);
    }


}
