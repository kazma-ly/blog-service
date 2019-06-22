package com.kazma233.blog.exception;

import com.kazma233.blog.enums.ResultEnums;

/**
 * 评论异常
 * @author kazma
 */
public class CommentException extends MyException {

    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Integer status) {
        super(message, status);
    }

    public CommentException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
