package com.tblog.blog_api.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.tblog.blog_api.entity.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MyUserDetails implements UserDetails {

    private Admin admin;

    private List<String> permissions;

    //List<GrantedAuthority> 如果存入到redis中会出问题，所以我们使用注解让他不序列化
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    public MyUserDetails(Admin admin, List<String> permissions) {
        this.admin = admin;
        this.permissions = permissions;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities!=null){
            return authorities;
        }
        authorities = new ArrayList<>();
        //把permissions中String类型的权限信息封装成GrantedAuthority对象（SimpleGrantedAuthority继承GrantedAuthority）
        //FIXME 方法1
        for (String permission : permissions) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }
        //FIXME 方法2 Sring流
        //List<SimpleGrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
