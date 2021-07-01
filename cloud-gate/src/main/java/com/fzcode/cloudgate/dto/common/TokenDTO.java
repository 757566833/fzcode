package com.fzcode.cloudgate.dto.common;

import lombok.Data;

@Data
public class TokenDTO {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
    public TokenDTO() {

    }
}
