package com.fzcode.internalcommon.dto.serviceauth.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;
}
