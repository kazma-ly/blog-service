package com.kazma233.blog.controller.manage.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.entity.role.vo.RoleAdd;
import com.kazma233.blog.entity.role.vo.RolePermissionsVO;
import com.kazma233.blog.entity.role.vo.RoleQuery;
import com.kazma233.blog.entity.role.vo.RoleUpdate;
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
    public BaseResult all(RoleQuery roleQuery) {
        PageInfo<RolePermissionsVO> pageInfo = roleService.queryAllArgs(roleQuery);

        return BaseResult.success(pageInfo);
    }

    @GetMapping("/role/{roleId}")
    public BaseResult all(@PathVariable(value = "roleId") String roleId) {
        RolePermissionsVO rolePermissionsVO = roleService.queryRoleAndPermissionByRoleId(roleId);

        return BaseResult.success(rolePermissionsVO);
    }

    @PostMapping("/role")
    public BaseResult add(@Validated RoleAdd roleAdd) {
        roleService.save(roleAdd);

        return BaseResult.success();
    }

    @DeleteMapping("/role/{roleId}")
    public BaseResult delete(@PathVariable("roleId") String roleId) {
        roleService.delete(roleId);

        return BaseResult.success();
    }

    @PutMapping("/role")
    public BaseResult update(@Validated RoleUpdate roleUpdate) {
        roleService.update(roleUpdate);

        return BaseResult.success();
    }

}
