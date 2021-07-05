package com.fzcode.internalcommon.dto.http;

import lombok.Data;

@Data
public class ErrorResponse {
    private Integer code ;
    private String message;
    private Object data;

    public ErrorResponse(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    public ErrorResponse(Integer code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
