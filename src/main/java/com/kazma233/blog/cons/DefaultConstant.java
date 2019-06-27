package com.kazma233.blog.cons;

import java.time.format.DateTimeFormatter;

public class DefaultConstant {

    /**
     * 登录时用于存储`key`的cookie的键
     */
    public static final String LOGIN_KEY = "LOGIN_KEY";

    /**
     * 文章缓存名字
     */
    public static final String ARTICLE_LIST_CACHE_NAME = "ARTICLE_LIST_CACHE";
    public static final String ARTICLE_CACHE_NAME = "ARTICLE_CACHE";

    // 日期格式化
    public static DateTimeFormatter DATE_FORMATTER_YM = DateTimeFormatter.ofPattern("yyyy-MM");

}
