package com.kazma233.blog.controller.manage.user;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.user.IRoleService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.user.RolePermissionsVO;
import com.kazma233.blog.vo.user.RoleQueryVO;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 *
 * @author kazma
 */
@RestController
@RequestMapping("/manage")
public class RoleManageController {

    @Autowired
    private IRoleService roleService;

    /**
     * 查询角色
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping("/roles")
    public BaseResult all(RoleQueryVO roleQueryVO) {
        roleQueryVO.init();
        PageInfo<RolePermissionsVO> pageInfo = roleService.queryByPage(roleQueryVO);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, pageInfo);
    }

    /**
     * 通过id查询角色
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping("/role/{roleId}")
    public BaseResult all(@PathVariable(value = "roleId") String roleId) {
        RolePermissionsVO rolePermissionsVO = roleService.queryRoleAndPermissionByRoleId(roleId);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, rolePermissionsVO);
    }

    /**
     * 增加角色
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PostMapping("/role")
    public BaseResult add(@Validated(AddGroup.class) Role role, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        Role r = roleService.insert(role);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, r);
    }

    /**
     * 删除角色
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping("/role/{roleId}")
    public BaseResult delete(@PathVariable("roleId") String roleId) {
        Integer res = roleService.delete(roleId);
        return BaseResult.createResult(res);
    }

    /**
     * 修改角色
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PutMapping("/role")
    public BaseResult update(@Validated(UpdateGroup.class) Role role, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        Integer res = roleService.update(role);
        return BaseResult.createResult(res);
    }

}
