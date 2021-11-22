package com.fzcode.internalcommon.dto.http;

import lombok.Data;

@Data
public class ErrorResponse {
    private String status ;
    private String message;
    private Object data;

    public ErrorResponse(String status,String message){
        this.status = status;
        this.message = message;
    }
    public ErrorResponse(String status,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
