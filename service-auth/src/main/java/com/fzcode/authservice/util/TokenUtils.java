package com.fzcode.authservice.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    private static SecretKey key;

    @Value("${auth.secret}")
    public void setKey(String secret) {
        System.out.println("secret" + secret);
        TokenUtils.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public static String createBearer(Integer aid,String username) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 3);

        Map<String, Object> header = new HashMap<>();
        header.put("email", username);
        header.put("aid", aid);
        System.out.println("JwtUtils.key:" + TokenUtils.key);
        String compactJws = Jwts.builder()
                .setHeader(header)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(c.getTime())
                .signWith(TokenUtils.key, SignatureAlgorithm.HS256)
                .compact();
        return "bearer " + compactJws;

    }

    public static String createBasic(String serviceName, String password) {
        return "basic " + serviceName+":"+password;
    }
}
