package com.fzcode.gateservice.dto.common;

public class TokenDTO {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
    public TokenDTO() {

    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
