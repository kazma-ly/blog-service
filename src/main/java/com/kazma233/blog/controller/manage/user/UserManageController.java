package com.kazma233.blog.controller.manage.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.group.SetRoleGroup;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.vo.UserQueryVO;
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
@RequestMapping("/manages")
@RequiresPermissions({"admin"})
public class UserManageController {

    private IUserService userService;

    @GetMapping(value = "/users")
    public BaseResult all(UserQueryVO userQueryVO) {
        userQueryVO.init();
        PageInfo<UserRoleVO> pageInfo = userService.queryUser(userQueryVO);

        return BaseResult.success(pageInfo);
    }

    @PutMapping(value = "/user")
    public BaseResult serUserPermission(@Validated(SetRoleGroup.class) User user) {
        userService.updateRole(user);

        return BaseResult.success(user);
    }

}
