package com.fzcode.service.file.dto;

public class SuccessResDTO {
    private Integer code = 100000;
    private String message;

    private Object data;

    public SuccessResDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    public SuccessResDTO(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
