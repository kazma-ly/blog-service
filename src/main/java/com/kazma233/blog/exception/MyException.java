package com.kazma233.blog.exception;

import com.kazma233.blog.enums.ResultEnums;

/**
 * @author zly
 * @date 2019/1/9
 **/
public class MyException extends RuntimeException {

    private Integer status;

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public MyException(ResultEnums resultEnums) {
        super(resultEnums.getMessage());
        this.status = resultEnums.getStatus();
    }

    public MyException() {
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
