package com.kazma233.blog.filter;

import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.WebTool;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * SpringMVC拦截器
 * Created by mac_zly on 16/8/21.
 */

// 已经改用shiro
// 拦截/manage/**
public class ManageFilter implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletRequest.setCharacterEncoding("UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");

        // 权限校验，不是所有人都可以操作manage
//        HttpSession session = httpServletRequest.getSession(false);
//        if (session != null && session.getAttribute("username") != null) {
//            return true;
//        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            httpServletResponse.getWriter().write("权限不足，请尝试登陆");
            return false;
        }

        // 查找uid 和 token
        String uid = null;
        String token = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {

            if (cookie.getName().equals("uid")) {
                uid = cookie.getValue();
                continue;
            }

            if (cookie.getName().equals("jwt_token")) {
                token = cookie.getValue();
            }
        }

        // 检查uid 和 token
        if (uid == null || token == null) {
            httpServletResponse.getWriter().write("Cookie错误，请尝试登陆");
            return false;
        }

        Claims claims = Jwts.parser()
                .requireIssuer(WebTool.MINE)
                .setSigningKey(WebTool.base64SecretKey())
                .parseClaimsJws(token).getBody();

        if (Objects.equals(claims.getSubject(), uid)) {
            // 权限校验
            Role role = null ;//userService.queryRoleByUid(Integer.parseInt(uid));
            if (role != null && "user:admin".equals(role.getPermissionIds()) && WebTool.MINE.equals(claims.getIssuer())) {
                return true;
            } else {
                httpServletResponse.getWriter().write("抱歉你的权限不足");
                return false;
            }
        } else {
            httpServletResponse.getWriter().write("Token异常，请尝试重新登陆");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if ("/welcome.html".equals(httpServletRequest.getRequestURI())) {
            httpServletResponse.setDateHeader("Expires", System.currentTimeMillis() + 2000 * 3600);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
