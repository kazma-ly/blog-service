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
    public static final String ARTICLE_CACHE_NAME = "ARTICLE_CACHE_KEY";

    public static final String ARTICLE_ONE = "ARTICLE_ONE";

    // 日期
    public static DateTimeFormatter DATE_FORMATTER_YM = DateTimeFormatter.ofPattern("yyyy-MM");

}
