package com.fzcode.internalcommon.dto.serviceauth.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GithubLoginRequest {
    @NotEmpty(message = "code不能为空")
    private String code;
}
