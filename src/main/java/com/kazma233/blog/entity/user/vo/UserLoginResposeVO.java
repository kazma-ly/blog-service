package com.kazma233.blog.entity.user.vo;

import com.kazma233.blog.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResposeVO {

    private Role role;
    private String uid;

}
