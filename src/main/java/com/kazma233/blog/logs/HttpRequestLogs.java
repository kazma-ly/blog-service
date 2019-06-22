package com.kazma233.blog.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpRequestLogs {

    private static final Logger LOGGER = LogManager.getLogger(HttpRequestLogs.class);

    // Pointcut表示要切哪个点
    // 第一个*表示任意的返回值，第二个*表示所有方法, ..表示任意参数
    @Pointcut(value = "execution(public * com.kazma233.blog.controller.*.*.*.*(..))")
    public void log() {
    }

    // Before表示在方法执行之前
    @Before("log()")
    public void before(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        // 记录http请求,url,method,client-ip,请求的方法,请求的参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();

        LOGGER.info("IP={} -> uri={}", request.getRemoteHost() + ":" + request.getRemotePort(), request.getRequestURI());
        // LOGGER.info("RequestUrl={}", request.getRequestURL());
        // LOGGER.info("Method={}", request.getMethod());

        // 类名
        // LOGGER.info("ClassMethod={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // 参数
        // LOGGER.info("ClassArgs={}", joinPoint.getArgs());
    }

    // After 表示在方法执行后
    // @After("log()")
    public void after() {
        // LOGGER.info("After=====================");
    }

    // 返回的值
    // @AfterReturning(pointcut = "log()", returning = "obj")
    public void afterReturning(Object obj) {
        if (obj == null) {
            LOGGER.info("Response={}\n", "void");
        } else {
            LOGGER.info("Response={}\n", obj.toString());
        }
    }

    // 环绕 包整个切面做了一个包装
    /*@Around("log()")
    public void wathController(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            // 方法的执行，也可以执行多次，或者不执行 进行阻塞
            proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }*/

}
