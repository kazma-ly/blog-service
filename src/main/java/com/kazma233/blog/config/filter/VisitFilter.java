package com.kazma233.blog.config.filter;

import com.kazma233.blog.entity.statistics.Visit;
import com.kazma233.blog.service.statistics.IVisitService;
import com.kazma233.blog.utils.ThreadPoolUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class VisitFilter implements HandlerInterceptor {

    private IVisitService visitService;

    private static final String NGINX_PROXY_IP_HEADER_NAME = "X-Real-IP";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ThreadPoolUtils.getCommonThreadPool().execute(() -> {
            visitService.save(
                    Visit.builder().
                            ip(request.getHeader(NGINX_PROXY_IP_HEADER_NAME)).
                            uri(request.getRequestURI()).
                            createTime(LocalDateTime.now()).
                            build()
            );
        });

        return true;
    }

}
