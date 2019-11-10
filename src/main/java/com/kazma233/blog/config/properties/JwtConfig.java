package com.kazma233.blog.config.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    private static String key;

    public static String getKey() {
        return key;
    }

    @Value("${blog.jwt.key}")
    public void setKey(String key) {
        JwtConfig.key = key;
    }
}
