package com.kazma233.blog.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Role {

    @NotBlank(message = "角色id为空", groups = {UpdateGroup.class})
    private String id;

    @NotBlank(message = "角色名称不能为空", groups = {AddGroup.class})
    private String roleName;

    @NotBlank(message = "权限ids不能为空", groups = {AddGroup.class})
    private String permissionIds;

    @NotBlank(message = "角色描述不能为空", groups = {AddGroup.class})
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}