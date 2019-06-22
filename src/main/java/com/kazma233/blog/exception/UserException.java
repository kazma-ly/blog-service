package com.kazma233.blog.exception;

import com.kazma233.blog.enums.ResultEnums;

/**
 * 用户异常
 * Created by mac_zly on 2017/3/22.
 */
public class UserException extends MyException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Integer status) {
        super(message);
    }

    public UserException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
