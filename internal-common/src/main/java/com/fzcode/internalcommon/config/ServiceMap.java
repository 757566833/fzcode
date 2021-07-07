package com.fzcode.internalcommon.config;

import lombok.Data;

@Data
public class ServiceMap {
    private ServiceParams auth;
    private ServiceParams file;
    private ServiceParams mail;
    private ServiceParams note;
}
