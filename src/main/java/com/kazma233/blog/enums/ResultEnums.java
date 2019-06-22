package com.kazma233.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误枚举
 * Created by mac_zly on 2017/3/22.
 */
@AllArgsConstructor
@Getter
public enum ResultEnums {
    UNKNOW_ERROR(-1, "发生了不可预知的错误，请稍后重试，或者自行检查"),
    RESOURCE_NOT_FOUND(-2, "资源不存在"),
    TOKEN_ERROR(-3, "Token 错误"),
    ILLEGAL_ERROR(-5, "非法访问"),

    UNKNOW_USER_ERROR(1000, "未知的用户"),
    LOGIN_FAIL(1001, "登录失败,检查用户名和密码"),
    CHECK_USER_FAIL(1002, "用户名或密码错误"),
    VERIFICATION_CODE_ERROR(1003, "验证码错误"),
    UPDATE_SUCCESS(1003, "更新成功"),
    UPDATE_FAIL(1004, "更新失败"),
    UPLOAD_FILE_SUCCESS(1005, "上传文件成功"),
    UPLOAD_ERROR(1006, "上传文件出现异常"),
    LOGIN_SUCCESS(1007, "登录成功"),

    FILE_MOVE_ERROR(1020, "文件移动失败"),
    SUCCESS(1100, "成功"),
    FAIL(1101, "失败"),
    NULL_DATA(1102, "有一些空数据"),
    PIC_NULL(1103, "图片为空"),

    QUERY_NULL_ERROR(1104, "查询结果为空"),

    FAVOURITE_SUCCESS(1201, "收藏成功"),
    FAVOURITE_FAIL(1202, "收藏失败"),
    FAVOURITE_CANCEL_SUCCESS(1203, "取消收藏成功"),
    FAVOURITE_CANCEL_FAIL(1204, "取消收藏失败"),
    FAVOURITE_EXIST(1205, "收藏存在"),
    UN_FAVOURITE_EXIST(1206, "收藏已经取消"),

    COMMENT_SUCCESS(1301, "评论成功"),
    COMMENT_FAIL(1302, "评论失败"),
    COMMENT_ERROR(1303, "评论异常"),

    NOT_FILE(1304, "没有这个文件"),
    DELETE_FILE_ERROR(1305, "文件删除失败"),
    NOT_FILE_INFO(1306, "没有这个文件信息"),

    QUERY_NOT_EXIST(1307, "查询结果不存在"),
    TEXT_NOT_EXITS(1308, "文章不存在");

    private Integer status;
    private String message;
}
