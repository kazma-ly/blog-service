package com.kazma233.blog.entity.role.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleUpdate {

    @NotBlank(message = "角色id不能为空")
    private String id;

    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @NotBlank(message = "权限ids不能为空")
    private String permissionIds;

    @NotBlank(message = "角色描述不能为空")
    private String description;

}
