package com.tblog.blog_api.handler;

import com.alibaba.fastjson.JSON;
import com.blog_api.vo.Result;
import com.tblog.blog_api.entity.SysUser;
import com.tblog.blog_api.service.LoginService;
import com.tblog.blog_api.utils.UserThreadLocal;
import com.tblog.blog_api.vo.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 在执行Controller方法（ Handler ）之前进行执行
         * 1.需要判断请求的接口路径是否为HandlerMethod（确实访问的是Controller的方法
         * 2.判断token是否为空，如果为空说明未登录
         * 3.如果token不为空，登录验证（loginService.checkToken
         * 4.如果认证成功，执行
         */


        if (!(handler instanceof HandlerMethod)){
            //handler 可能是RequestResourceHandler springboot 访问默认资源默认区classpath下的static目录去查询
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)){
            Result result = Result.FailedResponse(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser==null){
            Result result = Result.FailedResponse(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //登录验证成功，方形

        //如果要从Controller中直接获取用户信息，可以用ThreadLocal实现

        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除ThreadLocal中访问完的信息，会有内存泄露的问题
        UserThreadLocal.remove();
    }
}
