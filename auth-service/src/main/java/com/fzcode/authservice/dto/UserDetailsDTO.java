package com.fzcode.authservice.dto;

import com.fzcode.authservice.bean.MyUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserDetailsDTO implements MyUserDetails {
    private String username;
    private String password;
    private boolean expired;
    private boolean locked;
    private boolean enabled;
    private List<GrantedAuthority> authorities;
    private String registerType;

    public UserDetailsDTO(String username, String password, boolean expired, boolean locked, boolean enabled, List<GrantedAuthority> authorities,String registerType) {
        this.username = username;
        this.password = password;
        this.expired = expired;
        this.locked = locked;
        this.enabled = enabled;
        this.authorities = authorities;
        this.registerType = registerType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    /**
     * 账号是否过期
     * @return bool
     */
    @Override
    public boolean isAccountNonExpired() {
        return expired;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * 账号是否锁住
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }


    // 账号凭证是否过期 这个没鸟用
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 账号是否被冻结
     * @param
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }
}
