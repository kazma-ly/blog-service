package com.kazma233.blog.vo.user;

import com.kazma233.blog.entity.group.LoginGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zly
 * @date 2019/1/7
 **/
@Data
public class UserLoginVO {

    @NotBlank(message = "{message.username.length}", groups = {LoginGroup.class})
    private String username;
    @NotBlank(message = "{message.password.length}", groups = {LoginGroup.class})
    private String password;
    @NotBlank(message = "请输入验证码", groups = {LoginGroup.class})
    private String code;

}
