package com.kazma233.blog.filter;

import com.kazma233.blog.config.scheduling.VisitsMessageTask;
import com.kazma233.blog.entity.log.MongoLog;
import com.kazma233.common.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Component
public class AllVisitsCountFilter implements HandlerInterceptor {

    private static final List<String> NO_VIEW_LOG_URI = Arrays.asList("/manages", "/statistics");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUri = request.getRequestURI();
        for (String uri : NO_VIEW_LOG_URI) {
            if (requestUri.startsWith(uri)) {
                return true;
            }
        }

        VisitsMessageTask.VisitsHelp.addVisitsMessage(getVisitsMessage(request));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private MongoLog getVisitsMessage(HttpServletRequest request) {
        return MongoLog.builder().
                id(Utils.generateID()).
                ip(Utils.getClientIp(request)).
                path(request.getRequestURI()).
                visitTime(new Date()).
                build();
    }

}
