package com.fzcode.authservice.dto.common;

import javax.validation.constraints.NotEmpty;

public class TokenDTO {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
