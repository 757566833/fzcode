package com.fzcode.service.file.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Config {
    @Value("${filePath}")
    public String filePath ;

    @Value("${networkFilePath}")
    public String networkFilePath ;

}
