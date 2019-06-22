package com.kazma233.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ENABLE("ENABLE", "开启"),
    DISABLE("DISABLE", "关闭");

    private String code;
    private String desc;
}
