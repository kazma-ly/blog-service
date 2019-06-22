package com.kazma233.blog.exception;


import com.kazma233.blog.enums.ResultEnums;

/**
 * 查询异常
 * Created by mac_zly on 2017/3/29.
 */

public class QueryException extends UserException {

    public QueryException(String message, Integer status) {
        super(message, status);
    }

    public QueryException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
