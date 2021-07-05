package com.fzcode.internalcommon.dto.serviceauth.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateAuthorityRequest {
    @NotEmpty
    private String account;
    @NotEmpty
    private String authority;
}
