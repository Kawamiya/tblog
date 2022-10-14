package com.tblog.blog_api.security;

import com.tblog.blog_api.utils.JSONAuthentication;
import com.tblog.blog_api.vo.AdminErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.blog_api.vo.Result;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录用户访问SpringSecurity时的处理
 */
@Component("myAuthenticationEntryPoin")
public class MyAuthenticationEntryPoint extends JSONAuthentication implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = Result.FailedResponse(AdminErrorCode.USER_NOT_LOGIN.getCode(),AdminErrorCode.USER_NOT_LOGIN.getMsg());

        this.WriteJSON(request,response,result);
    }
}
