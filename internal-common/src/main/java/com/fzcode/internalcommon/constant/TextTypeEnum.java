package com.fzcode.internalcommon.constant;

import lombok.Getter;

public enum TextTypeEnum {
    MARKDOWN(1,"markdown"),
    RICHTEXT(2,"richText");
    @Getter
    private final int code;

    @Getter
    private final String value;

    private TextTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
