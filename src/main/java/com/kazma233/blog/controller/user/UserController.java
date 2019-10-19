package com.kazma233.blog.controller.user;

import com.google.common.io.ByteSource;
import com.kazma233.blog.entity.log.MongoFile;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.vo.UserPasswordUpdate;
import com.kazma233.blog.entity.user.vo.UserLoginResponse;
import com.kazma233.blog.entity.user.vo.UserLogin;
import com.kazma233.blog.entity.user.vo.UserRegister;
import com.kazma233.blog.service.user.IRoleService;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.ShiroUtils;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private IUserService userService;
    private IRoleService roleService;

    @PostMapping(value = "/login")
    public BaseResult doLogin(@RequestBody @Validated UserLogin user) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        usernamePasswordToken.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        if (!subject.isAuthenticated()) {
            return BaseResult.failed(Status.LOGIN_ERROR);
        }

        String uid = ShiroUtils.getUid();
        Role role = roleService.queryByRoleId(uid);

        return BaseResult.success(UserLoginResponse.builder().role(role).uid(uid).build());
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

    @GetMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    @PutMapping(value = "/password")
    public BaseResult changePassword(@RequestBody @Validated UserPasswordUpdate user) {
        userService.updatePassword(user);

        return BaseResult.success();
    }

    @PostMapping("/avatar")
    public BaseResult uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile) {
        userService.saveAvatar(avatarFile);

        return BaseResult.success();
    }

    @GetMapping("/avatar/{uid}")
    public void avatar(@PathVariable String uid, HttpServletResponse response) throws IOException {
        MongoFile avatarMongoFile = userService.getAvatarMongoFile(uid);
        response.setContentType(avatarMongoFile.getContentType());
        ByteSource.wrap(avatarMongoFile.getContent()).copyTo(response.getOutputStream());
    }

}