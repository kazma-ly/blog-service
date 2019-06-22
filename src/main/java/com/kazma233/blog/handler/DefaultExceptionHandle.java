package com.kazma233.blog.handler;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@Controller
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandle implements ErrorController {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResult missArgsException(Exception e) {
        log.error(request.getRequestURI() + ": Spring exception \n", e);
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return BaseResult.createResult(400, "请求方法错误", false);
        }
        if (e instanceof ServletRequestBindingException) {
            return BaseResult.createResult(400, "缺少请求参数", false);
        }

        return BaseResult.createResult(500, "服务器端出现错误，请稍后重试，或者联系我们", false);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MyException.class)
    public BaseResult myException(MyException ve) {
        log.error(request.getRequestURI() + ": 自定义异常: ", ve);
        return BaseResult.createResult(ve.getStatus(), ve.getMessage(), false);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ShiroException.class})
    public BaseResult sihroExceptionHandle(ShiroException e) {
        log.error(request.getRequestURI() + ": shiro异常: ", e);
        return BaseResult.createResult(403, "用户身份校验失败", false);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public BaseResult error() {
        return BaseResult.createFailResult(ResultEnums.RESOURCE_NOT_FOUND);
    }

}
