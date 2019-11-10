package com.kazma233.blog.cons;

import java.time.format.DateTimeFormatter;

public class DefaultConstant {

    public static final String ARTICLE_LIST_CACHE_KEY_NAME = "ARTICLE_LIST_CACHE_KEY";
    public static final String ARTICLE_CACHE_KEY_NAME = "ARTICLE_CACHE_KEY";

    // 日期格式化
    public static DateTimeFormatter DATE_FORMATTER_YM = DateTimeFormatter.ofPattern("yyyy-MM");

    // 用户注册的默认信息
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_ROLE = "1000";
    public static final String NORMAL_ROLE = "1001";

    public static final String MONGO_MATADATA_UID = "uid";
    public static final String MONGO_MATADATA_TYPE = "type";
    public static final String MONGO_MATADATA_TYPE_AVATAR = "avatar";

}
