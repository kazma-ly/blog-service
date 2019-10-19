package com.kazma233.blog.entity.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRoleUpdate {

    @NotBlank(message = "用户id不能为空")
    private String uid;

    @NotBlank(message = "用户权限id不能为空")
    private String roleId;

}
