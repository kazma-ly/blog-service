package com.kazma233.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnvStatus {
    RELEASE("release", "生产"),
    DEBUG("debug", "测试");

    private String code;
    private String desc;
}
