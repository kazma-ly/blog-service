package com.kazma233.blog.entity.permission.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissionUpdate {

    @NotBlank(message = "id不能为空")
    private String id;

    private String permissionName;

    private String permissionDescription;

}
