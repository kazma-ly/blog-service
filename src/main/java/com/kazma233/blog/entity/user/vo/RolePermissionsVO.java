package com.kazma233.blog.entity.user.vo;

import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.entity.user.Role;
import lombok.Data;

import java.util.List;

/**
 * @author zly
 * @date 2019/2/28
 **/

@Data
public class RolePermissionsVO {

    private Role role;
    private List<Permission> permissions;

}
