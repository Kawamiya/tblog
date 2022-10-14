package com.tblog.blog_api.security;

import com.tblog.blog_api.utils.JSONAuthentication;
import com.tblog.blog_api.vo.AdminErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import com.blog_api.vo.Result;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("myAccessDeniedHandler")
public class MyAccessDeniedHandler extends JSONAuthentication implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = Result.FailedResponse(AdminErrorCode.NO_PERMISSION.getCode(),AdminErrorCode.NO_PERMISSION.getMsg());
        this.WriteJSON(request,response,result);
    }
}
