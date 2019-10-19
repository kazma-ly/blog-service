package com.kazma233.blog.entity.role.vo;

import com.kazma233.blog.entity.common.vo.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQuery extends Query {

    private String roleName;

    private String description;

}
