package com.fzcode.apiblog.config;

import com.fzcode.internalcommon.config.ApiMap;
import com.fzcode.internalcommon.config.CloudMap;
import com.fzcode.internalcommon.config.ServiceMap;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "services")
@Data
public class Services {
    private CloudMap cloud;
    private ServiceMap service;
    private ApiMap api;
}
