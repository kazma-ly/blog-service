package com.kazma233.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

/**
 * 适用于当前工程的工具类
 * Created by mac_zly on 2017/4/28.
 */

public class WebTool {

    public static final String MINE = "MikuSama";

    public static String base64SecretKey() {
        Base64.Encoder encoder = Base64.getEncoder();
        return Arrays.toString(encoder.encode("MikuSama".getBytes(Charset.forName("UTF-8"))));
    }

    /**
     * 监听到session的属性变化时，就会往RequestContextHolder里面写本地线程的东西
     * 可以直接获得request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest get() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

}
