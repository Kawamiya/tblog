package com.tblog.blog_api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 负责封装输出JSON格式的类
 * MyAuthenticationFailureHandler
 */
public abstract class JSONAuthentication {

    protected void WriteJSON(HttpServletRequest request,
                             HttpServletResponse response,
                             Object data) throws IOException, ServletException{
        response.setContentType("application/json;charset=UTF-8");
        //跨域设置
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Method","POST,GET");

        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(data) );
        out.flush();
        out.close();
    }
}
