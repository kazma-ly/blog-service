package com.kazma233.blog.controller.manage.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.entity.user.vo.UserQuery;
import com.kazma233.blog.entity.user.vo.UserRoleUpdate;
import com.kazma233.blog.entity.user.vo.UserRoleVO;
import com.kazma233.blog.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/manages/users")
@RequiresPermissions({"admin"})
public class UserManageController {

    private IUserService userService;

    @GetMapping
    public BaseResult all(UserQuery userQueryVO) {
        PageInfo<UserRoleVO> pageInfo = userService.queryUser(userQueryVO);

        return BaseResult.success(pageInfo);
    }

    @PutMapping(value = "/role")
    public BaseResult updateUserRole(@Validated UserRoleUpdate userRoleUpdate) {
        userService.updateRole(userRoleUpdate);

        return BaseResult.success();
    }

}
