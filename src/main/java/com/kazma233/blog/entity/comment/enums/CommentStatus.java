package com.kazma233.blog.entity.comment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatus {
    SHOW("SHOW", "展示"),
    HIDDEN("HIDDEN", "不展示"),
    ;
    private String code;
    private String desc;
}
