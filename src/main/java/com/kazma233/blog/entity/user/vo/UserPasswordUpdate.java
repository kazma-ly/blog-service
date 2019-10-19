package com.kazma233.blog.entity.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserPasswordUpdate {

    @NotBlank(message = "请输入用户名")
    private String username;

    @NotBlank(message = "请输入密码")
    private String password;

    @NotBlank(message = "请输入新密码")
    private String newPasswrod;

}
