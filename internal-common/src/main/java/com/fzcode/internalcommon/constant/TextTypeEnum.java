package com.fzcode.internalcommon.constant;

import lombok.Getter;

public enum TextTypeEnum {
    MARKDOWN(1,"markdown"),
    RICHTEXT(2,"richText");
    @Getter
    private final int code;
    @Getter
    private final String value;
    private static final TextTypeEnum[] VALUES ;
    static {
        VALUES=values();
    }
    public static TextTypeEnum resolve(int code) {
        for (TextTypeEnum textTypeEnum:VALUES) {
            if (textTypeEnum.code == code) {
                return textTypeEnum;
            }
        }
        return null;
    }
    public static TextTypeEnum  valueOf(int code){
        TextTypeEnum typeEnum = resolve(code);
        if (typeEnum == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return typeEnum;
    }
    private TextTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
