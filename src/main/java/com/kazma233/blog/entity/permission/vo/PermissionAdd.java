package com.kazma233.blog.entity.permission.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissionAdd {

    @NotBlank(message = "权限名字不能为空")
    private String permissionName;

    @NotBlank(message = "权限描述不能为空")
    private String permissionDescription;

}
