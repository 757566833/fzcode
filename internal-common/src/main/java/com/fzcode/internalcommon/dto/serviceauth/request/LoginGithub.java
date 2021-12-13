package com.fzcode.internalcommon.dto.serviceauth.request;

import lombok.Data;

@Data
public class LoginGithub {
    private String code;
    private String redirect;
}
