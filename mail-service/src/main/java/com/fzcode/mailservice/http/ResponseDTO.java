package com.fzcode.mailservice.http;

public class ResponseDTO {

    private String code ;
    private Object data;
    private String message;

    public Object getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
