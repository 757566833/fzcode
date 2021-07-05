package com.fzcode.internalcommon.constant;

import lombok.Getter;

public enum RegisterTypeEnum {
    GITHUB_REGISTER(1,"github"),
    WEB_REGISTER(2,"web");
    @Getter
    private final int code;

    @Getter
    private final String value;

    private RegisterTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}
