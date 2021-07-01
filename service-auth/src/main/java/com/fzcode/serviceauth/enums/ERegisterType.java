package com.fzcode.serviceauth.enums;

public enum ERegisterType {
    GITHUB("1", "github"), REGISTER("2", "register");
    private String code;
    private String name;

    private ERegisterType(String code, String name) {
        this.code = code;
        this.name = name();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}