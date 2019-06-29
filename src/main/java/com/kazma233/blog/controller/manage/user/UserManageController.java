package com.kazma233.blog.controller.manage.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.group.SetRoleGroup;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.user.UserQueryVO;
import com.kazma233.blog.vo.user.UserRoleVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class UserManageController {

    @Autowired
    private IUserService userService;

    @RequiresPermissions({"admin"})
    @GetMapping(value = "/users")
    public BaseResult all(UserQueryVO userQueryVO) {
        userQueryVO.init();
        PageInfo<UserRoleVO> pageInfo = userService.queryUser(userQueryVO);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, pageInfo);
    }

    @RequiresPermissions({"admin"})
    @PutMapping(value = "/user")
    public BaseResult serUserPermission(@Validated(SetRoleGroup.class) User user, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        userService.updateRole(user);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, user);
    }

}
