package com.fzcode.internalcommon.exception;

public class CustomizeException extends Exception {
    private static final long serialVersionUID = 1l;
    private String status;
    public CustomizeException(String status,String message){
        super(message);
        this.status = status;
    }
    public String getStatus(){
        return  this.status;
    }
}
