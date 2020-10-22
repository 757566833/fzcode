package com.fzcode.authservice.bean;

import org.springframework.security.core.userdetails.UserDetails;

public interface MyUserDetails extends UserDetails {
    String getRegisterType();
}