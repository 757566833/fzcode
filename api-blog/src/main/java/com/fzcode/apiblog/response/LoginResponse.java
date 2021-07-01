package com.fzcode.api.blog.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;
}
