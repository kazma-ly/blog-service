package com.kazma233.blog.cons;

import java.time.format.DateTimeFormatter;

public class DefaultConstant {

    /**
     * 文章缓存名字
     */
    public static final String ARTICLE_LIST_CACHE_NAME = "ARTICLE_LIST_CACHE";
    public static final String ARTICLE_CACHE_NAME = "ARTICLE_CACHE";

    // 日期格式化
    public static DateTimeFormatter DATE_FORMATTER_YM = DateTimeFormatter.ofPattern("yyyy-MM");

    // 用户注册的默认信息
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_ROLE = "1000";
    public static final String NORMAL_ROLE = "1001";

}
