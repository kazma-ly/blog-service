package com.kazma233.blog.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.dao.user.PermissionDao;
import com.kazma233.blog.entity.permission.Permission;
import com.kazma233.blog.entity.permission.vo.PermissionAdd;
import com.kazma233.blog.entity.permission.vo.PermissionQuery;
import com.kazma233.blog.entity.permission.vo.PermissionUpdate;
import com.kazma233.blog.service.user.IPermissionService;
import com.kazma233.blog.utils.id.IDGenerater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class PermissionService implements IPermissionService {

    private PermissionDao permissionDao;

    @Override
    public PageInfo queryAllByCondition(PermissionQuery permissionQueryVO) {
        PageHelper.startPage(permissionQueryVO.getPageNo(), permissionQueryVO.getPageSize());
        List<Permission> permissionList = permissionDao.queryPermissionByCondition(permissionQueryVO);

        return new PageInfo<>(permissionList);
    }

    @Override
    public void save(PermissionAdd permissionAdd) {
        permissionDao.insert(
                Permission.builder().
                        id(IDGenerater.generateID()).
                        createTime(new Date()).
                        permissionDescription(permissionAdd.getPermissionDescription()).
                        permissionName(permissionAdd.getPermissionName()).
                        build()
        );
    }

    @Override
    public void updateById(PermissionUpdate permissionUpdate) {
        permissionDao.updateById(permissionUpdate);
    }

    @Override
    public void deleteById(String id) {
        permissionDao.deleteById(id);
    }

    @Override
    public List<Permission> queryByIds(String[] ids) {
        return permissionDao.queryPermissionByIds(ids);
    }
}
