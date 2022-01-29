package com.fzcode.serviceauth.util;

import com.fzcode.serviceauth.config.Secret;
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

@Component
public class TokenUtils {
    private SecretKey key;
    private Secret secret;
    @Autowired
    public void setSecret(Secret secret) {
        this.secret = secret;
    }
    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret.getSecret()));
    }

    public  String createBearer(String aid,String uid,String email) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 3);
        String compactJws = Jwts.builder()
                .setSubject(uid)
                .setIssuedAt(new Date())
                .setExpiration(c.getTime())
                .signWith(this.key, SignatureAlgorithm.HS256)
                .claim("aid",aid)
                .claim("uid",uid)
                .claim("email",email)
                .compact();
        return "bearer " + compactJws;

    }
}
