package com.kazma233.blog.entity.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegister {

    @NotBlank(message = "{message.username.length}")
    private String username;

    @NotBlank(message = "{message.password.length}")
    private String password;

}
