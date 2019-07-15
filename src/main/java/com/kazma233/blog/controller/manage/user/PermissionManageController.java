package com.kazma233.blog.controller.manage.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.entity.user.vo.PermissionQueryVO;
import com.kazma233.blog.service.user.IPermissionService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/manages")
@RequiresPermissions(value = {"admin"})
public class PermissionManageController {

    private IPermissionService permissionService;

    @GetMapping("/permissions")
    public BaseResult all(PermissionQueryVO permissionQueryVO) {
        permissionQueryVO.init();
        PageInfo pageInfo = permissionService.queryAllByCondition(permissionQueryVO);

        return BaseResult.success(pageInfo);
    }

    @PostMapping("/permission")
    public BaseResult add(@Validated(AddGroup.class) @RequestBody Permission permission) {
        permissionService.save(permission);

        return BaseResult.success(permission);
    }

    @DeleteMapping("/permission/{pid}")
    public BaseResult delete(@PathVariable("pid") String pid) {
        permissionService.deleteById(pid);

        return BaseResult.success();
    }

    @DeleteMapping("/permission")
    public BaseResult deletes(@RequestParam("pids") List<String> pids) {
        permissionService.deleteByIds(pids);

        return BaseResult.success();
    }

    @PutMapping("/permission")
    public BaseResult update(@RequestBody @Validated(UpdateGroup.class) Permission permission) {
        permissionService.updateById(permission);

        return BaseResult.success();
    }

}
