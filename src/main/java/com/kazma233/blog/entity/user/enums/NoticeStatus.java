package com.kazma233.blog.entity.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeStatus {
    ENABLE("ENABLE", "开启通知"),
    DISABLE("DISABLE", "关闭通知");

    private String code;
    private String msg;
}
