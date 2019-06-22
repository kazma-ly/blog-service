package com.kazma233.blog.controller.manage.user;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.user.IPermissionService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.user.PermissionQueryVO;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理
 *
 * @author kazma
 */
@RestController
@RequestMapping("/manage")
public class PermissionManageController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 查询权限 通过权限名字查询权限 可模糊查询
     *
     * @param permissionQueryVO 查询条件
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping("/permissions")
    public BaseResult all(PermissionQueryVO permissionQueryVO) {
        permissionQueryVO.init();
        PageInfo pageInfo = permissionService.queryPermissionByCondition(permissionQueryVO);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, pageInfo);
    }

    /**
     * 通过权限ID查找
     */
//    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
//    @GetMapping("/permission/{roleId}")
//    public BaseResult queryByRoleId(@PathVariable("roleId") String roleId) {
//        RoleInfoDTO roleInfoDTO = permissionService.queryByRoleId(roleId);
//        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, roleInfoDTO);
//    }

    /**
     * 增加权限
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PostMapping("/permission")
    public BaseResult add(@Validated(AddGroup.class) @RequestBody Permission permission, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        Integer result = permissionService.addPermission(permission);
        return BaseResult.createResult(result, permission);
    }

    /**
     * 删除权限
     *
     * @param pid 权限id
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping("/permission/{pid}")
    public BaseResult delete(@PathVariable("pid") String pid) {
        Integer res = permissionService.deletePermissionById(pid);
        return BaseResult.createResult(res);
    }

    /**
     * 批量删除权限
     *
     * @param pids 权限ids
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping("/permission")
    public BaseResult deletes(@RequestParam("pids") List<String> pids) {
        Integer res = permissionService.deletePermissionByIds(pids);
        return BaseResult.createResult(res);
    }

    /**
     * 修改权限
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PutMapping("/permission")
    public BaseResult update(@RequestBody @Validated(UpdateGroup.class) Permission permission, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        Integer res = permissionService.updatePermissionById(permission);
        return BaseResult.createResult(res);
    }

}
