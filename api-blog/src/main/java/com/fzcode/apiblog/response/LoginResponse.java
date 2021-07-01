package com.fzcode.apiblog.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;
}
