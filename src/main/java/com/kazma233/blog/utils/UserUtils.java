package com.kazma233.blog.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.user.exception.UserException;
import com.kazma233.blog.entity.user.vo.UserJwtVO;
import com.kazma233.blog.utils.jwt.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class UserUtils {

    private static Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static UserJwtVO getUserInfo() {
        String auth = SpringContextHelper.getRequest().getHeader("auth");
        String userJwtString = JwtUtils.validationJwtAndGetSubject(auth);
        return GSON.fromJson(userJwtString, UserJwtVO.class);
    }

    public static String getUserId() {
        return getUserInfo().getId();
    }

    // == password ==

    public static String encodePw(String originPw) {
        byte[] newPwByte = originPw.getBytes(StandardCharsets.UTF_8);
        return DigestUtils.sha256Hex(newPwByte);
    }

    public static void checkUserPw(String inputPw, String dbPw) {
        if (!encodePw(inputPw).equals(dbPw)) {
            throw new UserException(Status.LOGIN_ERROR);
        }
    }
}
