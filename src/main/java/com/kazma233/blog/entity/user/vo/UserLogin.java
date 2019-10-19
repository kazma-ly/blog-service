package com.kazma233.blog.entity.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin {

    @NotBlank(message = "{message.username.length}")
    private String username;

    @NotBlank(message = "{message.password.length}")
    private String password;

}
