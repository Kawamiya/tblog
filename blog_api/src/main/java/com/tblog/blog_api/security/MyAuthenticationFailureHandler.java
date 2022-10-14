package com.tblog.blog_api.security;

import com.tblog.blog_api.utils.JSONAuthentication;
import com.tblog.blog_api.vo.AdminErrorCode;
import org.springframework.security.authentication.*;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.blog_api.vo.Result;
import org.springframework.stereotype.Component;

@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends JSONAuthentication implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
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

//        response.setContentType("text/Json;charset=utf-8");
//        response.getWriter().write(new ObjectMapper().writeValueAsString(result) );
        this.WriteJSON(request,response,result);
    }
}
