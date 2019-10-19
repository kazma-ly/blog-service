package com.kazma233.blog.entity.user.vo;

import com.kazma233.blog.entity.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {

    private Role role;
    private String uid;

}
