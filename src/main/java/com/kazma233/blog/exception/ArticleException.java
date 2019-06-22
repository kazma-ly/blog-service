package com.kazma233.blog.exception;

import com.kazma233.blog.enums.ResultEnums;

/**
 * @author zly
 * @date 2019/1/9
 **/
public class ArticleException extends MyException {

    public ArticleException(String message) {
        super(message);
    }

    public ArticleException(String message, Integer status) {
        super(message, status);
    }

    public ArticleException(ResultEnums resultEnums) {
        super(resultEnums);
    }

}
