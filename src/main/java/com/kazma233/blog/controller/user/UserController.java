package com.kazma233.blog.controller.user;

import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.entity.user.vo.UserLogin;
import com.kazma233.blog.entity.user.vo.UserPasswordUpdate;
import com.kazma233.blog.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private IUserService userService;

    @PostMapping(value = "/login")
    public BaseResult doLogin(@RequestBody @Validated UserLogin user) {

        return BaseResult.success(userService.login(user));
    }

    @PutMapping(value = "/password")
    public BaseResult changePassword(@RequestBody @Validated UserPasswordUpdate user) {
        userService.updatePassword(user);

        return BaseResult.success();
    }

}