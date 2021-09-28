package com.fzcode.internalcommon.constant;

import lombok.Getter;

public enum RedisDBEnum {
    AUTH(0,"auth"),
    Mail(15,"mail");
    @Getter
    private final int code;

    @Getter
    private final String value;

    private RedisDBEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
