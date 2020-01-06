package com.kazma233.blog.config.handler;

import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.common.exception.parent.CustomizeException;
import com.kazma233.blog.entity.user.exception.UserException;
import com.kazma233.blog.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Controller
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @ExceptionHandler(Exception.class)
    public BaseResult exception(Exception e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        if (e instanceof HttpRequestMethodNotSupportedException) {
            return BaseResult.failed(Status.UN_SUPPORT_METHOD);
        }

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            return BaseResult.failed(Status.ARGS_ERROR, ValidationUtils.getErrorMessage(manve.getBindingResult()));
        }

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(request.getRequestURI() + " has an error: ", e);

        return BaseResult.failed(Status.UN_KNOW_ERROR);
    }

    @ExceptionHandler(value = CustomizeException.class)
    public BaseResult myException(CustomizeException ve) {
        if (ve instanceof UserException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return BaseResult.failed(ve);
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        log.error(request.getRequestURI() + " -> custom exception: ", ve);

        return BaseResult.failed(ve);
    }

}
