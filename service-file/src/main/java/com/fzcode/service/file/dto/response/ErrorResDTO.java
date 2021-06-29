package com.fzcode.service.file.dto.response;

public class ErrorResDTO {
    private Integer code ;
    private String message;

    private Object data;

    public ErrorResDTO(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public ErrorResDTO(Integer code, String message) {
        this.code = code;
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
