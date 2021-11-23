package com.fzcode.internalcommon.exception;

import org.springframework.http.HttpStatus;

public class CustomizeException extends Exception {
    private static final long serialVersionUID = 1l;
    private HttpStatus status;
    public CustomizeException(HttpStatus status,String message){
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus(){
        return  this.status;
    }
}
