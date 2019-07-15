package com.kazma233.blog.service.user;


import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.entity.user.vo.PermissionQueryVO;

import java.util.List;

public interface IPermissionService {

    PageInfo queryAll(Integer page, Integer once);

    PageInfo queryAllByCondition(PermissionQueryVO permissionQueryVO);

    void save(Permission permission);

    void updateById(Permission permission);

    void deleteById(String id);

    void deleteByIds(List<String> ids);

    List<Permission> queryByIds(String[] ids);
}
