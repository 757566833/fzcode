package com.fzcode.internalcommon.dto.fileblog.response;

import lombok.Data;

@Data
public class UrlResponse {
    private String url;
    public UrlResponse(String url){
        this.url = url;
    }
}
