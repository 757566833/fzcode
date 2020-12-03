package com.fzcode.authservice.dto.response;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class LoginResDTO {

    private String token;
    private String role;

    public LoginResDTO(String token, String  role) {
        this.token = token;
        this.role = role;
    }
}
