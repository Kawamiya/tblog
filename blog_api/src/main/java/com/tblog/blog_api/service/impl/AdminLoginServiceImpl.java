package com.tblog.blog_api.service.impl;

import com.alibaba.fastjson.JSON;
import com.blog_api.vo.Result;
import com.tblog.blog_api.entity.Admin;
import com.tblog.blog_api.entity.SysUser;
import com.tblog.blog_api.security.MyUserDetails;
import com.tblog.blog_api.service.AdminLoginService;
import com.tblog.blog_api.utils.JWTUtils;
import com.tblog.blog_api.vo.AdminErrorCode;
import com.tblog.blog_api.vo.params.AdminLoginParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(AdminLoginParam adminLoginParam) {
        /**
         * 1、获取AuthenticationManager authenticate进行用户认证
         * 2、如果认证失败，则给出对应提示
         * 3、如果认证通过，使用JWT生成token
         * 4、将token存入redis中
         */

        //获取AuthenticationManager authenticate进行用户认证
        //登录时只用2个的UsernamePasswordAuthenticationToken构造方法，因为认证状态未确认
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(adminLoginParam.getUsername(),adminLoginParam.getPassword());
        try{
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            System.out.println(1);
            //如果认证失败，则给出对应提示
            if (Objects.isNull(authenticate)){
                throw new RuntimeException("登录失败");
            }

            //如果认证通过，获得当前登录的admin用户
            Object principal = authenticate.getPrincipal();
            MyUserDetails myUserDetails = (MyUserDetails) principal;
            Admin admin = myUserDetails.getAdmin();
            Long adminId = admin.getId();
            //根据admin的用户id生成JWT的token
            String token = JWTUtils.createToken(adminId);
            //将token存入redis中
            //此时需要将myUserDetails而不是admin传入redis，因为后面认证过滤器是：如果有token，将myUserDetails设置到SecurityContextHolder中
            redisTemplate.opsForValue().set("ADMIN_TOKEN_"+token, JSON.toJSONString(myUserDetails), 1, TimeUnit.DAYS);
            return Result.SuccessResponse(token);


        }catch (AuthenticationException e){
            Result result = null;
            if(e instanceof AccountExpiredException){
                //账号过期
                result = Result.FailedResponse(AdminErrorCode.USER_ACCOUNT_EXPIRED.getCode(),AdminErrorCode.USER_ACCOUNT_EXPIRED.getMsg());
            }else if(e instanceof BadCredentialsException){
                //密码错误
                result = Result.FailedResponse(AdminErrorCode.USER_CREDENTIALS_ERROR.getCode(),AdminErrorCode.USER_CREDENTIALS_ERROR.getMsg());
            }else if(e instanceof CredentialsExpiredException) {
                //密码过期
                result = Result.FailedResponse(AdminErrorCode.USER_CREDENTIALS_EXPIRED.getCode(),AdminErrorCode.USER_CREDENTIALS_EXPIRED.getMsg());
            }else if(e instanceof DisabledException){
                //账号不可用
                result = Result.FailedResponse(AdminErrorCode.USER_ACCOUNT_DISABLE.getCode(),AdminErrorCode.USER_ACCOUNT_DISABLE.getMsg());
            }else if (e instanceof LockedException){
                //账号锁定
                result = Result.FailedResponse(AdminErrorCode.USER_ACCOUNT_LOCKED.getCode(),AdminErrorCode.USER_ACCOUNT_LOCKED.getMsg());
            }else if (e instanceof InternalAuthenticationServiceException){
                //用户不存在
                result = Result.FailedResponse(AdminErrorCode.USER_ACCOUNT_NOT_EXIST.getCode(),AdminErrorCode.USER_ACCOUNT_NOT_EXIST.getMsg());
            }else{
                result = Result.FailedResponse(AdminErrorCode.I_DONT_KNOW.getCode(),AdminErrorCode.I_DONT_KNOW.getMsg());
            }
            return result;
        }

    }

    @Override
    public MyUserDetails checkToken(String token) {
        //是否为空，解析是否成功，redis是否存在
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap==null){
            return null;
        }
        String myUserDetailsJson = redisTemplate.opsForValue().get("ADMIN_TOKEN_" + token);
        if (StringUtils.isBlank(myUserDetailsJson)){
            return null;
        }
        MyUserDetails myUserDetails = JSON.parseObject(myUserDetailsJson, MyUserDetails.class);
        return myUserDetails;
    }
}
