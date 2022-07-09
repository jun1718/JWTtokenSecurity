package com.example.security_jwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultJwt;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {
    private String secretkey = "zerock12345678";
    private long expire = 60 * 24 * 30;

    public String generateToken(String content) throws Exception {
        return Jwts.builder()
            .setIssuedAt(new Date())
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
            .claim("sub", content)
            .signWith(SignatureAlgorithm.HS256, secretkey.getBytes("UTF-8"))
            .compact();
    }

    public String validateAndExtract(String tokenStr) throws Exception {
        String contentValue = null;

        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                .setSigningKey(secretkey.getBytes("UTF-8"))
                    .parseClaimsJws(tokenStr);

            log.info("", defaultJws);
            log.info("", defaultJws.getBody().getClass());

//            DefaultJwt jwt;
//            jwt.getBody()
            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
            contentValue = claims.getSubject(); //
        } catch (Exception e) {
            e.printStackTrace();
            contentValue = null;
        }

        return contentValue;
    }
}
