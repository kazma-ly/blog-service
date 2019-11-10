package com.kazma233.blog.controller.user;

import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.vo.UserLogin;
import com.kazma233.blog.entity.user.vo.UserPasswordUpdate;
import com.kazma233.blog.entity.user.vo.UserRegister;
import com.kazma233.blog.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private IUserService userService;

    @PostMapping(value = "/login")
    public BaseResult doLogin(@RequestBody @Validated UserLogin user) {

        return BaseResult.success(userService.login(user));
    }

    @PostMapping(value = "/register")
    public BaseResult doRegister(@RequestBody @Validated UserRegister user) {

        return BaseResult.success(userService.register(user));
    }

    @GetMapping("/info")
    public BaseResult info() {
        UserInfo userInfo = userService.getUserInfo();

        return BaseResult.success(userInfo);
    }

    @PutMapping(value = "/info")
    public BaseResult updateInfo(@RequestBody @Validated(UserInfo.Update.class) UserInfo userInfo) {
        userService.updateUserInfo(userInfo);

        return BaseResult.success();
    }

    @PutMapping(value = "/password")
    public BaseResult changePassword(@RequestBody @Validated UserPasswordUpdate user) {
        userService.updatePassword(user);

        return BaseResult.success();
    }

    @PostMapping("/avatar")
    public BaseResult uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile) throws IOException {
        userService.saveAvatar(avatarFile);

        return BaseResult.success();
    }

    @GetMapping("/avatar/{id}")
    public void avatar(@PathVariable String id, HttpServletResponse response) throws IOException {
        userService.getAvatar(id, response);
    }

}