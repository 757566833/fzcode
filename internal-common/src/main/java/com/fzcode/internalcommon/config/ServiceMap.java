package com.fzcode.internalcommon.config;

import lombok.Data;

@Data
public class ServiceMap {
    public ServiceParams auth;
    public ServiceParams file;
    public ServiceParams mail;
    public ServiceParams note;
}
