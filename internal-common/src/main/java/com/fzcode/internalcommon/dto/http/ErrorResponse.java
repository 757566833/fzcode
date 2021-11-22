package com.fzcode.internalcommon.dto.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ErrorResponse {
    private String status ;
    private String error;
    private Object data;

    public ErrorResponse(){

    }
    public ErrorResponse(String status,String error){
        this.status = status;
        this.error = error;
    }
    public ErrorResponse(String status,String error,Object data){
        this.status = status;
        this.error = error;
        this.data = data;
    }

}
