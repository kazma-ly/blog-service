package com.kazma233.blog.controller.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kazma233.blog.config.properties.WebSettings;
import com.kazma233.blog.cons.DefaultConstant;
import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.LoginGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.entity.mongo.MongoFile;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.exception.UserException;
import com.kazma233.blog.service.user.IRoleService;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.user.UserChangePwVO;
import com.kazma233.blog.vo.user.UserLoginVO;
import com.kazma233.common.PhotoTokenUtils;
import com.kazma233.common.Utils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private WebSettings webSettings;

    /**
     * 单位秒 code存储的时间长度
     */
    private static final Integer MAX_CODE_LIFE = 3 * 60;

    /**
     * 缓存
     * 用于存储验证码
     */
    private static Cache<String, String> CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(MAX_CODE_LIFE, TimeUnit.SECONDS)
            .build();

    @PostMapping(value = "/login")
    public BaseResult doLogin(@CookieValue(value = DefaultConstant.LOGIN_KEY) String key,
                              @RequestBody @Validated(LoginGroup.class) UserLoginVO user, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        if (!checkCode(key, user.getCode())) {
            throw new UserException(ResultEnums.VERIFICATION_CODE_ERROR);
        }

        // shiro 认证
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        // usernamePasswordToken.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        if (subject.isAuthenticated()) {
            User authUser = (User) subject.getSession().getAttribute("user");

            Map<String, Object> res = new HashMap<>(2);
            Role role = roleService.queryByRoleId(authUser.getRoleId());
            res.put("role", role);
            res.put("uid", authUser.getId());
            return BaseResult.createSuccessResult(ResultEnums.LOGIN_SUCCESS, res);
        }

        return BaseResult.createFailResult(ResultEnums.LOGIN_FAIL);
    }

    @PostMapping(value = "/register")
    public BaseResult doRegister(@RequestBody @Validated(AddGroup.class) User user, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());

        String uid = userService.register(user);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, uid);
    }

    @GetMapping(value = "/logout")
    public void logout(HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public BaseResult changePassword(@RequestBody @Validated(UpdateGroup.class) UserChangePwVO user,
                                     BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());

        int res = userService.updatePassword(user);

        return BaseResult.createResult(res);
    }

    @RequestMapping(value = "/token/pic", method = RequestMethod.GET)
    public void picToken(HttpServletResponse response) throws Exception {
        PhotoTokenUtils photoTokenUtils = new PhotoTokenUtils();

        String key = Utils.generateID();

        // 下方一个cookie
        Cookie cookie = new Cookie(DefaultConstant.LOGIN_KEY, key);
        cookie.setMaxAge(MAX_CODE_LIFE);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain(webSettings.getCookieHost());
        response.addCookie(cookie);

        CACHE.put(key, photoTokenUtils.token());
        photoTokenUtils.out(response);
    }

    @RequestMapping(value = "/avatar/{uid}", method = RequestMethod.GET)
    public void getHeadImage(@PathVariable String uid, HttpServletResponse response,
                             @RequestParam(value = "clip") boolean clip) throws Exception {
        MongoFile mongoFile = userService.queryAvatar(uid, clip);
        response.setContentType(mongoFile.getContentType());
        if (mongoFile.getContent() != null) {
            IOUtils.write(mongoFile.getContent(), response.getOutputStream());
        }
    }

    @RequiresAuthentication
    @RequestMapping(value = "/avatar/{uid}", method = RequestMethod.POST)
    public BaseResult uploadAvatar(@RequestParam("file") MultipartFile multipartFile,
                                   @PathVariable(value = "uid") String uid) {

        Integer res = userService.insertAvatar(uid, multipartFile);
        return BaseResult.createResult(res);
    }

    private boolean checkCode(String key, String inputCode) {
        // 自己生成验证码
        String verificationCode = CACHE.getIfPresent(key);
        CACHE.invalidate(key);
        // 验证码为空或者不相等
        return inputCode.equalsIgnoreCase(verificationCode);
    }

}