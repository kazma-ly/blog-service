package com.kazma233.blog.entity.search.exception;

import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.entity.common.exception.parent.CustomizeException;

public class SearchException extends CustomizeException {

    public SearchException(Status status) {
        super(status);
    }

}
