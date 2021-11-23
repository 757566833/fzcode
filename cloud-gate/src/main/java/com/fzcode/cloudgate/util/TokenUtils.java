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
    private static SecretKey key;
    private Secret secret;

    @Autowired
    public void setSecret(Secret secret) {
        this.secret = secret;
    }
    @PostConstruct
    public void init(){
        TokenUtils.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret.getSecret()));
    }
    public static String createBearer(String username) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 3);

        Map<String, Object> header = new HashMap<String, Object>();
        header.put("email", username);

        String compactJws = Jwts.builder()
                .setHeader(header)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(c.getTime())
                .signWith(TokenUtils.key, SignatureAlgorithm.HS256)
                .compact();
        return "bearer "+compactJws;

    }

    public static TokenInfoDTO parseBearer(String token) {
        int preIndex = token.indexOf("bearer ");
        if(preIndex==-1){
            throw new JwtException("token格式不对");
        }
        String jwsStr = token.substring(7);
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey(TokenUtils.key)
                .build()
                .parseClaimsJws(jwsStr);
        Object emailObj = jws.getHeader().get("email");
        Object aidObj = jws.getHeader().get("aid");
        TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
        tokenInfoDTO.setAid(Integer.parseInt(aidObj.toString()));
        tokenInfoDTO.setEmail(emailObj.toString());
        return tokenInfoDTO;
    }

    public static String createBasic(String serviceName, String password) {
        return "basic " + serviceName+":"+password;
    }
}
