package com.kazma233.blog.controller.manage.user;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.group.SetRoleGroup;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.user.UserQueryVO;
import com.kazma233.blog.vo.user.UserRoleVO;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/manage")
public class UserManageController {

    @Autowired
    private IUserService userService;

    /**
     * 查询所有用户
     * 进行管理
     */
    @RequiresPermissions({"admin"})
    @GetMapping(value = "/users")
    public BaseResult all(UserQueryVO userQueryVO) {
        userQueryVO.init();
        PageInfo<UserRoleVO> pageInfo = userService.queryUser(userQueryVO);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, pageInfo);
    }

    /**
     * 更新用户角色
     */
    @RequiresPermissions({"admin"})
    @PutMapping(value = "/user")
    public BaseResult serUserPermission(@Validated(SetRoleGroup.class) User user, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        userService.updateRole(user);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, user);
    }

}
