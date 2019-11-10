package com.kazma233.blog.utils.jwt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kazma233.blog.config.properties.JwtConfig;
import com.kazma233.blog.utils.date.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class JwtUtils {

    private static Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private static final SecretKey SECRET_KEY = generalKey();

    public static String getLoginToken(Object subject) {
        return Jwts.builder().
                setSubject(GSON.toJson(subject)).
                setExpiration(DateUtils.dateTimeToDateZH(LocalDateTime.now().plusHours(12))).
                signWith(SECRET_KEY).
                compact();
    }

    public static String validationJwtAndGetSubject(String jwt) {
        Claims claims = Jwts.parser().
                setSigningKey(SECRET_KEY).
                parseClaimsJws(jwt).
                getBody();

        return claims.getSubject();
    }

    private static SecretKey generalKey() {
        byte[] encodedKey = JwtConfig.getKey().getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, SignatureAlgorithm.HS256.getJcaName());
    }

}
