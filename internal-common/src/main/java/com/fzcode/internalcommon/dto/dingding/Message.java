package com.fzcode.internalcommon.dto.dingding;

import lombok.Data;

@Data
public class Message {
    private String msgType;
    private MessageInfo text;
}
