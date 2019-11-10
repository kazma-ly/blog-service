package com.kazma233.blog.config.filter;

import com.google.common.base.Strings;
import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.user.exception.UserException;
import com.kazma233.blog.utils.jwt.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String auth = request.getHeader("auth");
        if (Strings.isNullOrEmpty(auth)) {
            throw new UserException(Status.UN_AUTH_ERROR);
        }

        try {
            JwtUtils.validationJwtAndGetSubject(auth);
            return true;
        } catch (JwtException e) {
            log.error("jwt 校验失败: ", e);
            throw new UserException(Status.UN_AUTH_ERROR);
        }
    }
}
