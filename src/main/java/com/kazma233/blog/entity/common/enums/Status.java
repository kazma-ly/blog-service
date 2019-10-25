package com.kazma233.blog.entity.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    UN_KNOW_ERROR(1000, "发生了不可预知的错误，请稍后重试，或者自行检查"),
    RESOURCE_NOT_FOUND(1001, "资源不存在"),
    UN_SUPPORT_METHOD(1002, "请求方法错误"),
    UN_AUTH_ERROR(1003, "请先登录"),
    ILLEGAL_ERROR(1004, "非法访问"),


    LOGIN_ERROR(1006, "登录失败，请检查用户名或密码"),
    ARGS_ERROR(1007, "参数不正确"),
    USER_NOT_FOUND_ERROR(1008, "未知用户"),

    SUCCESS(1100, "成功"),
    FAIL(1101, "失败"),

    CATEGORY_ALREADY_IN_USE(1010, "该分类还在使用"),

    SOME_SEARCH_ENGINE_INDEX_ERROR(1011, "搜索引擎处理索引发生异常"),
    SEARCH_ENGINE_ERROR(1012, "搜索时发生异常"),
    SEARCH_ENGINE_BEAN_TO_MAP_ERROR(1013, "Bean to map error"),

    HTTP_ERROR(1014, "请求URL发生异常"),
    ARTICLE_EMPTY(1015, "文章内容为空"),
    ;

    private Integer status;
    private String message;
}
