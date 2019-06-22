package com.kazma233.blog.exception;

import com.kazma233.blog.enums.ResultEnums;

/**
 * 数据校验异常
 * Created by mac_zly on 2017/4/27.
 */

public class ValidatedException extends MyException {


    public ValidatedException(String message) {
        super(message);
    }

    public ValidatedException(String message, Integer status) {
        super(message);
    }

    public ValidatedException(ResultEnums resultEnums) {
        super(resultEnums);
    }

}
