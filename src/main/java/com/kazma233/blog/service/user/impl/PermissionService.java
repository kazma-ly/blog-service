package com.kazma233.blog.service.user.impl;

import com.kazma233.blog.dao.user.PermissionDao;
import com.kazma233.blog.dao.user.RoleDao;
import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.service.user.IPermissionService;
import com.kazma233.blog.vo.user.PermissionQueryVO;
import com.kazma233.common.SecretTool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 权限Service层
 *
 * @author kazma
 */
@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo queryAllPermission(Integer page, Integer once) {
        PageHelper.startPage(page, once);
        List<Permission> permissionList = permissionDao.queryAll();
        return new PageInfo<>(permissionList);
    }

    @Override
    public PageInfo queryPermissionByCondition(PermissionQueryVO permissionQueryVO) {
        PageHelper.startPage(permissionQueryVO.getPage(), permissionQueryVO.getCount());
        List<Permission> permissionList = permissionDao.queryPermissionByCondition(permissionQueryVO);
        return new PageInfo<>(permissionList);
    }

    @Transactional
    @Override
    public Integer addPermission(Permission permission) {
        String pid = SecretTool.getInstance().generateValue();
        permission.setId(pid);
        permission.setCreateTime(new Date());
        return permissionDao.insert(permission);
    }

    @Transactional
    @Override
    public Integer updatePermissionById(Permission permission) {
        return permissionDao.updateById(permission);
    }

    @Transactional
    @Override
    public Integer deletePermissionById(String id) {
        return permissionDao.deleteById(id);
    }

    @Transactional
    @Override
    public Integer deletePermissionByIds(List<String> ids) {
        return permissionDao.deleteByIds(ids);
    }

    @Override
    public List<Permission> queryPermissionByIds(String[] ids) {
        return permissionDao.queryPermissionByIds(ids);
    }
}
