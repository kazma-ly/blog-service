package com.kazma233.blog.filter;

import com.kazma233.blog.dao.mongo.MongoLogDao;
import com.kazma233.blog.entity.mongo.MongoLog;
import com.kazma233.common.ThreadPoolUtils;
import com.kazma233.common.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class AllVisitsCountFilter implements HandlerInterceptor {

    @Autowired
    private MongoLogDao mongoLogDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AllVisitsCountFilter.class);

    private static final List<String> NO_VIEW_LOG_URI = Arrays.asList("/manage", "/statistics");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ThreadPoolUtils.getCachedThreadPool().execute(() -> insertVisitInfo(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void insertVisitInfo(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/manage") || requestURI.startsWith("/statistics")) {
            return;
        }

        for (String uri : NO_VIEW_LOG_URI) {
            if (requestURI.startsWith(uri)) {
                return;
            }
        }

        MongoLog mongoLog = new MongoLog();
        mongoLog.setId(Utils.generateID());
        mongoLog.setIp(getClientIp(request));
        mongoLog.setPath(requestURI);
        mongoLog.setVisitTime(new Date());
        try {
            mongoLogDao.insert(mongoLog);
        } catch (Exception e) {
            LOGGER.error("插入浏览量发生错误", e);
        }
    }

    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

}
