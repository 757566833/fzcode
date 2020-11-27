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
public class JwtUtils {
    private static SecretKey key;

    @Value("${auth.secret}")
    public void setKey(String secret) {
        System.out.println("secret" + secret);
        JwtUtils.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public static String createToken(String username) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 3);

        Map<String, Object> header = new HashMap<String, Object>();
        header.put("email", username);
        System.out.println("JwtUtils.key:" + JwtUtils.key);
        String compactJws = Jwts.builder()
                .setHeader(header)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(c.getTime())
                .signWith(JwtUtils.key, SignatureAlgorithm.HS256)
                .compact();
        return "bearer "+compactJws;

    }

    public static String parseToken(String token) {
        int preIndex = token.indexOf("bearer ");
        if(preIndex==-1){
            throw new JwtException("token格式不对");
        }
        String jwsStr = token.substring(7);
        Object uid = "";
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(JwtUtils.key)
                    .build()
                    .parseClaimsJws(jwsStr);
            uid = jws.getHeader().get("email");
        } catch (JwtException e) {
            throw e;
        }

        return uid.toString();
    }
}
