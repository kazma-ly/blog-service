package com.kazma233.blog.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Permission {

    @NotNull(message = "权限id不能为空", groups = {UpdateGroup.class})
    private String id;

    @NotBlank(message = "权限名字不能为空", groups = {AddGroup.class})
    private String permissionName;

    @NotBlank(message = "权限描述不能为空", groups = {AddGroup.class})
    private String permissionDescription;

    /**
     * `@DateTimeFormat`指的是 页面提交时指定的日期格式
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}