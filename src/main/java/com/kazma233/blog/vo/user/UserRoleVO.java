package com.kazma233.blog.vo.user;

import com.kazma233.blog.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zly
 * @date 2019/1/11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleVO extends User {

    private String roleName;
    private String description;

}
