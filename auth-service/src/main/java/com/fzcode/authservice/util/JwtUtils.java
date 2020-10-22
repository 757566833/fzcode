package com.fzcode.authservice.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("ko3678678576fjdksljfdlksjfkldsjflkdsjflksdjlkfjdslkfjldksjfkldsjflkds567567567567576567546134hrg3iy1"));
//    private static SecretKey key =Keys.hmacShaKeyFor(signingKeySecret.getBytes())

    public static String createToken(String username) {
        System.out.println("1");
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
                .signWith(key, SignatureAlgorithm.HS512)
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
            System.out.println("jwt过期了");
        }

        return uid.toString();
    }
}
