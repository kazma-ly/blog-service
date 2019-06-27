package com.kazma233.blog.service.user;


import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.vo.user.PermissionQueryVO;

import java.util.List;

public interface IPermissionService {

    public PageInfo queryAll(Integer page, Integer once);

    public PageInfo queryAllByCondition(PermissionQueryVO permissionQueryVO);

    public Integer save(Permission permission);

    public Integer updateById(Permission permission);

    public Integer deleteById(String id);

    public Integer deleteByIds(List<String> ids);

    public List<Permission> queryByIds(String[] ids);
}
