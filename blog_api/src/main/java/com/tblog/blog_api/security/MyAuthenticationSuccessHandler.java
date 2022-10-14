package com.tblog.blog_api.security;


import com.tblog.blog_api.utils.JSONAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.blog_api.vo.Result;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends JSONAuthentication implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //在SpringSecurity登录验证成功时，调用此方法返回想要的Json数据
        Result result = Result.SuccessResponse(200, "SpringSecurity成功登录", null);
        //由于此方法并不是用return返回数据，所以我们调用response来给前端返回
        //将Json赛道HttpServletResponse中返回前台
//        response.setContentType("text/Json;charset=utf-8");
//        response.getWriter().write(new ObjectMapper().writeValueAsString(result) );
        this.WriteJSON(request,response,result);
    }
}
