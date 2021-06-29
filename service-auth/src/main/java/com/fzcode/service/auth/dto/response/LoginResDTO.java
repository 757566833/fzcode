package com.fzcode.service.auth.dto.response;

import lombok.Data;


@Data
public class LoginResDTO {

    private String token;
    private String role;

    public LoginResDTO(String token, String  role) {
        this.token = token;
        this.role = role;
    }
}
