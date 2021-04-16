package com.fzcode.authservice.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("deprecation")
public class Mysql extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    }
}
