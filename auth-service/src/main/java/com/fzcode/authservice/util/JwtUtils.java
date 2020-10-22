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
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public static String createToken(String username) {
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
                .signWith(JwtUtils.key, SignatureAlgorithm.HS512)
                .compact();
        return compactJws;

    }

    public static String parseToken(String token) {

        Object uid = "";
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            uid = jws.getHeader().get("email");
        } catch (JwtException e) {
            e.printStackTrace();
            System.out.println("token过期了");
        }

        return uid.toString();
    }
}
