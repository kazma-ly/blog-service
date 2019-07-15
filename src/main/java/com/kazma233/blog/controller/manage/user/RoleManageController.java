package com.kazma233.blog.controller.manage.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.vo.RolePermissionsVO;
import com.kazma233.blog.entity.user.vo.RoleQueryVO;
import com.kazma233.blog.service.user.IRoleService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/manages")
@RequiresPermissions(value = {"admin"})
public class RoleManageController {

    private IRoleService roleService;

    @GetMapping("/roles")
    public BaseResult all(RoleQueryVO roleQueryVO) {
        roleQueryVO.init();
        PageInfo<RolePermissionsVO> pageInfo = roleService.queryAllArgs(roleQueryVO);

        return BaseResult.success(pageInfo);
    }

    @GetMapping("/role/{roleId}")
    public BaseResult all(@PathVariable(value = "roleId") String roleId) {
        RolePermissionsVO rolePermissionsVO = roleService.queryRoleAndPermissionByRoleId(roleId);

        return BaseResult.success(rolePermissionsVO);
    }

    @PostMapping("/role")
    public BaseResult add(@Validated(AddGroup.class) Role role) {
        roleService.save(role);

        return BaseResult.success();
    }

    @DeleteMapping("/role/{roleId}")
    public BaseResult delete(@PathVariable("roleId") String roleId) {
        roleService.delete(roleId);

        return BaseResult.success();
    }

    @PutMapping("/role")
    public BaseResult update(@Validated(UpdateGroup.class) Role role) {
        roleService.update(role);

        return BaseResult.success();
    }

}
