package com.kazma233.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

public class WebTool {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static final String MINE = "MikuSama";

    public static String base64SecretKey() {
        Base64.Encoder encoder = Base64.getEncoder();
        return Arrays.toString(encoder.encode(MINE.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 监听到session的属性变化时，就会往RequestContextHolder里面写本地线程的东西
     * 可以直接获得request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

}
