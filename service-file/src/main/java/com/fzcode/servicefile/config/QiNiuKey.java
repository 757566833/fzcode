package com.fzcode.servicefile.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiNiuKey {
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
