package com.kazma233.blog.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 阅读量拦截器
 */

//@Order(3)
//@WebFilter(filterName = "visitsCountFilter", urlPatterns = "/api/v2/text/info/*")
public class ArticleVisitsCountFilter implements HandlerInterceptor {

    public static final ConcurrentHashMap<String, Integer> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    private static final Pattern PATTERN = Pattern.compile("/article/\\b\\w{32}\\b");

    private static ExecutorService EXECUTOR = new ThreadPoolExecutor(
            100,
            200,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(200)
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!"GET".equals(request.getMethod())) {
            return true;
        }

        EXECUTOR.execute(() -> {
            String uri = request.getRequestURI();
            if (isMatch(uri)) {
                CONCURRENT_HASH_MAP.merge(uri, 1, (oldValue, value) -> oldValue + value);
            }
        });

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        Integer readCount = concurrentHashMap.get(request.getRequestURI());
//        request.setAttribute("readCount", readCount);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    // URL 匹配
    private boolean isMatch(String content) {
        Matcher matcher = PATTERN.matcher(content);
        return matcher.find();
    }
}
