package com.kazma233.blog.exception;

import com.kazma233.blog.enums.ResultEnums;

public class SearchException extends MyException {

    public SearchException(String message) {
        super(message);
    }

    public SearchException(String message, Integer status) {
        super(message, status);
    }

    public SearchException(ResultEnums resultEnums) {
        super(resultEnums);
    }

    public SearchException() {
    }
}
