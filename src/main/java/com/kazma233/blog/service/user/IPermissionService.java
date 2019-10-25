package com.kazma233.blog.service.user;


import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.permission.Permission;
import com.kazma233.blog.entity.permission.vo.PermissionAdd;
import com.kazma233.blog.entity.permission.vo.PermissionQuery;
import com.kazma233.blog.entity.permission.vo.PermissionUpdate;

import java.util.List;

public interface IPermissionService {

    PageInfo queryAllByCondition(PermissionQuery permissionQueryVO);

    void save(PermissionAdd permissionAdd);

    void updateById(PermissionUpdate permissionUpdate);

    void deleteById(String id);

    List<Permission> queryByIds(String[] ids);
}
