package com.kazma233.blog.exception;

import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.exception.parent.CustomizeException;

public class ValidatedException extends CustomizeException {

    public ValidatedException(Status status, String message) {
        super(status, message);
    }

}
