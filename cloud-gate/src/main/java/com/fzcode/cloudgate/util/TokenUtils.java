package com.fzcode.cloudgate.util;

import com.fzcode.cloudgate.config.Secret;
import com.fzcode.internalcommon.dto.common.TokenInfoDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class  TokenUtils {
    private  SecretKey key;
    private Secret secret;

    @Autowired
    public void setSecret(Secret secret) {
        this.secret = secret;
    }
    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret.getSecret()));
    }

    public  TokenInfoDTO parseBearer(String token) {
        int preIndex = token.indexOf("bearer ");
        if(preIndex==-1){
            throw new JwtException("token格式不对");
        }
        String jwsStr = token.substring(7);
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(jwsStr);
        Object emailObj = jws.getHeader().get("email");
        Object aidObj = jws.getHeader().get("aid");
        String uid = jws.getBody().getSubject();
        TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
        tokenInfoDTO.setAid(aidObj.toString());
        tokenInfoDTO.setEmail(emailObj.toString());
        tokenInfoDTO.setUid(uid);
        return tokenInfoDTO;
    }

    public  String createBasic(String serviceName, String password) {
        return "basic " + serviceName+":"+password;
    }
}
