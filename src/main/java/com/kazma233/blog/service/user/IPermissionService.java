package com.kazma233.blog.service.user;


import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.vo.user.PermissionQueryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 权限service
 */
public interface IPermissionService {

    /**
     * 查询所有的权限
     */
    public PageInfo queryAllPermission(Integer page, Integer once);

    /**
     * @param permissionQueryVO 查询条件
     */
    public PageInfo queryPermissionByCondition(PermissionQueryVO permissionQueryVO);

    /**
     * 新增权限
     */
    public Integer addPermission(Permission permission);

    /**
     * 修改权限
     */
    public Integer updatePermissionById(Permission permission);

    /**
     * 删除权限
     */
    public Integer deletePermissionById(String id);

    /**
     * 批量删除权限
     */
    public Integer deletePermissionByIds(List<String> ids);

    public List<Permission> queryPermissionByIds(String[] ids);
}
