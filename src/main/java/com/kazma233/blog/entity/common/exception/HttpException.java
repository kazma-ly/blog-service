package com.kazma233.blog.entity.common.exception;

import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.common.exception.parent.CustomizeException;

public class HttpException extends CustomizeException {

    public HttpException(Status status) {
        super(status);
    }

}
