package com.fzcode.fileblog.config;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QiNiuAuth {

    private QiNiuKey qiNiuKey;
    public static Auth auth;
    public static String uploadToken;
    public static Configuration configuration;
    @Autowired
    public void setQiNiuKey(QiNiuKey qiNiuKey){
        this.qiNiuKey = qiNiuKey;
    }
    @PostConstruct
    public void  init (){
        QiNiuAuth.auth =  Auth.create(qiNiuKey.getAccessKey(), qiNiuKey.getSecretKey());
        QiNiuAuth.uploadToken =  QiNiuAuth.auth.uploadToken(qiNiuKey.getBucketName());
        QiNiuAuth.configuration = new Configuration(Region.region2());
    }
}
