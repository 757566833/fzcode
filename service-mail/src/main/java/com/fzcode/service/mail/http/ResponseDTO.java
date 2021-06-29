package com.fzcode.service.mail.http;

public class ResponseDTO {

    private String code ;
    private Object data;
    private String message;

    public ResponseDTO() {
    }

    public ResponseDTO(String code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

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
