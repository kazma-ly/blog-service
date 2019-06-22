package com.kazma233.blog.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 安全这块的拦截器
 */

//@Order(2)
//@WebFilter(filterName = "securityFilter", urlPatterns = "/**")
//上面两种是普通Servlet的Filter
public class SecurityFilter implements HandlerInterceptor {

    /**
     * 限流处理
     */
    private static final AtomicInteger CURRENT_CONNECT = new AtomicInteger(200);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return CURRENT_CONNECT.getAndDecrement() > 0;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CURRENT_CONNECT.getAndIncrement();
    }
}
