package com.fzcode.internalcommon.dto.http;

import lombok.Data;

@Data
public class SuccessResponse {
    private Integer code = 100000;
    private String message;

    private Object data;
    public  SuccessResponse(String message,Object data){
        this.message=message;
        this.data = data;
    }
}
