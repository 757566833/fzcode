package com.fzcode.cloudgate.util;

import com.fzcode.cloudgate.config.Secret;
import com.fzcode.internalcommon.dto.common.TokenInfoDTO;
import com.fzcode.internalcommon.exception.CustomizeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;


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

    public TokenInfoDTO parseBearer(String token) throws CustomizeException {
        int preIndex = token.indexOf("bearer ");
        if(preIndex==-1){
            throw new CustomizeException(HttpStatus.UNAUTHORIZED,"token格式不对");
        }
        String jwsStr = token.substring(7);
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(jwsStr)
                    .getBody();
            Date expiration = claims.getExpiration();
            if(new Date(System.currentTimeMillis()).after(expiration)){
                throw new CustomizeException(HttpStatus.UNAUTHORIZED,"token 超时");
            }
        }catch (ExpiredJwtException e){
            throw new CustomizeException(HttpStatus.UNAUTHORIZED,"token 失效");
        };
        String email = claims.get("email").toString();
        String aid = claims.get("aid").toString();
        String uid = claims.get("uid").toString();
        TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
        tokenInfoDTO.setAid(aid);
        tokenInfoDTO.setEmail(email);
        tokenInfoDTO.setUid(uid);
        return tokenInfoDTO;
    }

    public  String createBasic(String serviceName, String password) {
        return "basic " + serviceName+":"+password;
    }
}
