package com.kazma233.blog.entity.user.vo;

import com.kazma233.blog.entity.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zly
 * @date 2019/1/4
 **/
@Data
public class UserChangePwVO {
    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名", groups = {UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "请输入密码", groups = {UpdateGroup.class})
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码", groups = UpdateGroup.class)
    private String newPasswrod;

}
