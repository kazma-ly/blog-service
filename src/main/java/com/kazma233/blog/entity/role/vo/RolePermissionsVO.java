package com.kazma233.blog.entity.role.vo;

import com.kazma233.blog.entity.permission.Permission;
import com.kazma233.blog.entity.role.Role;
import lombok.Data;

import java.util.List;

@Data
public class RolePermissionsVO {

    private Role role;
    private List<Permission> permissions;

}
