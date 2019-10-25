package com.kazma233.blog.controller.manage.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.permission.vo.PermissionAdd;
import com.kazma233.blog.entity.permission.vo.PermissionQuery;
import com.kazma233.blog.entity.permission.vo.PermissionUpdate;
import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.service.user.IPermissionService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/manages/permissions")
@RequiresPermissions(value = {"admin"})
public class PermissionManageController {

    private IPermissionService permissionService;

    @GetMapping
    public BaseResult all(PermissionQuery permissionQuery) {
        PageInfo pageInfo = permissionService.queryAllByCondition(permissionQuery);

        return BaseResult.success(pageInfo);
    }

    @PostMapping
    public BaseResult add(@Validated @RequestBody PermissionAdd permissionAdd) {
        permissionService.save(permissionAdd);

        return BaseResult.success();
    }

    @DeleteMapping("/{pid}")
    public BaseResult delete(@PathVariable("pid") String pid) {
        permissionService.deleteById(pid);

        return BaseResult.success();
    }

    @PutMapping
    public BaseResult update(@RequestBody @Validated PermissionUpdate permissionUpdate) {
        permissionService.updateById(permissionUpdate);

        return BaseResult.success();
    }

}
