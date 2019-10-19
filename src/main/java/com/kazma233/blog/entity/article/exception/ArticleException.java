package com.kazma233.blog.entity.article.exception;

import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.entity.common.exception.parent.CustomizeException;

public class ArticleException extends CustomizeException {

    public ArticleException(Status status) {
        super(status);
    }

}
