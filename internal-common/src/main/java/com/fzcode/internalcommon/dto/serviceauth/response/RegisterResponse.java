package com.fzcode.internalcommon.dto.serviceauth.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private String token;
    private String role;
}
