package com.tblog.blog_api.config;

import com.tblog.blog_api.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyLogoutHandler myLogoutHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired
    private AuthenticationTokenFilter authenticationTokenFilter;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        //加密策略 MD5 不安全 彩虹表  MD5 加盐
        String mszlu = new BCryptPasswordEncoder().encode("mszlu");
        System.out.println(mszlu);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //不通过session方式获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //.antMatchers("/articles/**").authenticated()//登录成功就可以放行
                //.antMatchers("/articles/**").access("@authService.auth(request,authentication)")
                .antMatchers("/admin/login").anonymous()
                .antMatchers("/admin/logout").anonymous()


                .antMatchers("/admin/**").authenticated()//登录成功就可以放行
                .antMatchers("/admin/**").access("@authService.auth(request,authentication)")

                .antMatchers("/tags/**").authenticated()//登录成功就可以放行
                .antMatchers("/admin/**").access("@authService.auth(request,authentication)")
                .antMatchers("/admin/**").authenticated()//登录成功就可以放行
                .antMatchers("/admin/**").access("@authService.auth(request,authentication)")
                .antMatchers("/admin/**").authenticated()//登录成功就可以放行
                .antMatchers("/admin/**").access("@authService.auth(request,authentication)")
//                .antMatchers("/tags/**").authenticated()//登录成功就可以放行
//                .antMatchers("/tags/**").access("@authService.auth(request,authentication)")



                //TODO
                //和loginform相关的一堆处理器配置如果不用loginform的登录接口，那么全都不起作用

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .accessDeniedHandler(myAccessDeniedHandler)

                //login
//                .and()
//                .formLogin()
//                .loginPage("/admin/login") //自定义的登录页面
//                .loginProcessingUrl("/admin/login") //登录处理接口
//                .usernameParameter("username") //定义登录时的用户名的key 默认为username
//                .passwordParameter("password") //定义登录时的密码key，默认是password
//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(myAuthenticationFailureHandler)
//                //.failureUrl("/test/adminlogin")
//                //.defaultSuccessUrl("/test/adminlogin")
//                .permitAll()


                //logout
                .and()
                .logout() //退出登录配置
                .logoutUrl("/admin/logout") //退出登录接口
                //.logoutSuccessUrl("/admin/login")
                .addLogoutHandler(myLogoutHandler)
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")//注销之后删除cookie
                .permitAll() //退出登录的接口放行

        ;
        http.addFilterBefore(authenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);

/*      //session版，使用默认的登录处理器进行处理
        http.authorizeRequests() //开启登录认证
                //.antMatchers("/admin/**").access("@authService.auth(request,authentication)")
                //.antMatchers("/admin/permission/**").authenticated()//登录成功就可以放行
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/articles/**").authenticated()//登录成功就可以放行
                .antMatchers("/articles/**").access("@authService.auth(request,authentication)")
                //login
                .and()
                .formLogin()
                .loginPage("/admintest/login") //自定义的登录页面
                .loginProcessingUrl("/admintest/login") //登录处理接口
                .usernameParameter("username") //定义登录时的用户名的key 默认为username
                .passwordParameter("password") //定义登录时的密码key，默认是password
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                //.failureUrl("/test/adminlogin")
                //.defaultSuccessUrl("/test/adminlogin")
                .permitAll()

                //访问需要权限的资源时，如果未登录
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                //访问需要权限的资源时，如果没有权限
                .accessDeniedHandler(myAccessDeniedHandler)

                //logout
                .and()
                .logout() //退出登录配置
                .logoutUrl("/admintest/logout") //退出登录接口
                //.logoutSuccessUrl("/admin/login")
                .addLogoutHandler(myLogoutHandler)
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")//注销之后删除cookie
                .permitAll() //退出登录的接口放行

                .and()
                .csrf().disable()
        ;
*/




//                .antMatchers("/user/findAll").hasRole("admin") //访问接口需要admin的角色
//                .antMatchers("/css/**").permitAll()
////                .antMatchers("/img/**").permitAll()
////                .antMatchers("/js/**").permitAll()
////                .antMatchers("/plugins/**").permitAll()
////                .antMatchers("/admin/**").access("@authService.auth(request,authentication)") //自定义service 来去实现实时的权限认证
////                .antMatchers("/pages/**").authenticated()//登录成功就可以放行
////                .and().formLogin()
////                .loginPage("/login.html") //自定义的登录页面
////                .loginProcessingUrl("/login") //登录处理接口
////                .usernameParameter("username") //定义登录时的用户名的key 默认为username
////                .passwordParameter("password") //定义登录时的密码key，默认是password
////                .defaultSuccessUrl("/pages/main.html")
////                .failureUrl("/login.html")
////                .permitAll() //通过 不拦截，更加前面配的路径决定，这是指和登录表单相关的接口 都通过
////                .and().logout() //退出登录配置
////                .logoutUrl("/logout") //退出登录接口
////                .logoutSuccessUrl("/login.html")
////                .permitAll() //退出登录的接口放行
////                .and()
////                .httpBasic()
////                .and()
////                .csrf().disable() //csrf关闭 如果自定义登录 需要关闭
////                .headers().frameOptions().sameOrigin();// 支持iframe页面嵌套

    }
}
