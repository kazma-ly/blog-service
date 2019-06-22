package com.kazma233.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleStatus {
    ENABLE("ENABLE", "启用文章"),
    DISABLE("DISABLE", "禁用文章");

    private String code;
    private String desc;
}
