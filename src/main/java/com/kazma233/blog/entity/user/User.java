package com.kazma233.blog.entity.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.SetRoleGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank(message = "部分信息为空", groups = {SetRoleGroup.class})
    private String id;

    @NotBlank(message = "{message.username.length}", groups = {UpdateGroup.class, AddGroup.class})
    private String username;

    @NotBlank(message = "{message.password.length}", groups = {UpdateGroup.class, AddGroup.class})
    private String password;

    @NotBlank(message = "请选择一个权限", groups = {SetRoleGroup.class})
    private String roleId;

    private String enable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
