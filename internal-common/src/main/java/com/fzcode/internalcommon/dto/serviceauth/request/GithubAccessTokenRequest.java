package com.fzcode.internalcommon.dto.serviceauth.request;

import lombok.Data;

@Data
public class GithubAccessTokenRequest {
    private String client_id;
    private String client_secret;
    private String code;
}
