package com.tblog.blog_api.security;

import com.alibaba.fastjson.JSON;
import com.tblog.blog_api.entity.Admin;
import com.tblog.blog_api.service.AdminLoginService;
import com.tblog.blog_api.utils.JWTUtils;
import com.tblog.blog_api.vo.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * 1、获取token
         * 2、解析token，获取admin（我们调用之前AdminLoginService定义的checktoken方法
         * 3、将admin信息存入SecurityContextHolder
         */

        //获取token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)){
            //如果没有token，放行
            filterChain.doFilter(request,response);
            //过滤器链是有放行和回传两部分功能的，放行以后还需要return回传，要不然会执行下面的代码
            return;
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isNotBlank(userJson)){
            filterChain.doFilter(request,response);
            return;
        }

        //解析token，获取MyUserDetails
        //MyUserDetails myUserDetails = adminLoginService.checkToken(token);
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap==null){
            filterChain.doFilter(request,response);
            return;
            //throw new RuntimeException("token非法");
        }
        String myUserDetailsJson = redisTemplate.opsForValue().get("ADMIN_TOKEN_" + token);
        if (StringUtils.isBlank(myUserDetailsJson)){
//            filterChain.doFilter(request,response);
//            return;
            throw new RuntimeException("token已过期or异常");
        }
        MyUserDetails myUserDetails = JSON.parseObject(myUserDetailsJson, MyUserDetails.class);

        if (myUserDetails==null){
            filterChain.doFilter(request,response);
            return;
        }

        //TODO 获取权限信息，封装到Authentication中
        //将admin信息存入SecurityContextHolder
        //此时用三个参数的UsernamePasswordAuthenticationToken构造方法，这种方法会让用户进入已认证状态

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                //new UsernamePasswordAuthenticationToken(myUserDetails,null,null);
                new UsernamePasswordAuthenticationToken(myUserDetails,null,myUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request,response);
    }
}
