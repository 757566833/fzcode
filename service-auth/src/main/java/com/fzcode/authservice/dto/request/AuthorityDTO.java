package com.fzcode.authservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthorityDTO {
    @NotEmpty
    private String account;
    @NotEmpty
    private String authority;
}
