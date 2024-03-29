package com.tblog.blog_api.security;

import com.tblog.blog_api.utils.JSONAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import com.blog_api.vo.Result;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 注销成功处理器
 */
@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler extends JSONAuthentication implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Result result = Result.SuccessResponse(200,"注销成功",null);
        this.WriteJSON(request,response,result);
    }
}
