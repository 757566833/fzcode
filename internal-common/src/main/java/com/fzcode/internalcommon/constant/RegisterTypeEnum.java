package com.fzcode.internalcommon.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum RegisterTypeEnum {

    GITHUB_REGISTER(1,"github"),
    WEB_REGISTER(2,"web");
    @Getter
    private final int code;

    @Getter
    private final String value;
    private static final RegisterTypeEnum[] VALUES ;
    static {
        VALUES=values();
    }
    public static RegisterTypeEnum resolve(int code) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (RegisterTypeEnum typeEnum:VALUES) {
            if (typeEnum.code == code) {
                return typeEnum;
            }
        }
        return null;
    }
    private RegisterTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
     public static RegisterTypeEnum  valueOf(int code){
        RegisterTypeEnum registerTypeEnum = resolve(code);
        if (registerTypeEnum == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return registerTypeEnum;
    }
}
