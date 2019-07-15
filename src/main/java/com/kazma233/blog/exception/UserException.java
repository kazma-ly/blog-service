package com.kazma233.blog.exception;

import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.exception.parent.CustomizeException;

public class UserException extends CustomizeException {

    public UserException(Status status) {
        super(status);
    }

}
