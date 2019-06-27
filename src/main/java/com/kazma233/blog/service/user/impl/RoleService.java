package com.kazma233.blog.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.dao.user.PermissionDao;
import com.kazma233.blog.dao.user.RoleDao;
import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.service.user.IRoleService;
import com.kazma233.blog.vo.user.RolePermissionsVO;
import com.kazma233.blog.vo.user.RoleQueryVO;
import com.kazma233.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageInfo<RolePermissionsVO> queryAllArgs(RoleQueryVO roleQueryVO) {
        PageHelper.startPage(roleQueryVO.getPage(), roleQueryVO.getCount());

        List<Role> roles = roleDao.queryAll(roleQueryVO);
        List<RolePermissionsVO> rolePermissionsVOS = new ArrayList<>(roles.size());
        roles.forEach(role -> {
            RolePermissionsVO rolePermissionsVO = new RolePermissionsVO();
            rolePermissionsVO.setRole(role);

            String permissionIds = role.getPermissionIds();
            String[] ids = permissionIds.split(",");
            List<Permission> permissions = permissionDao.queryPermissionByIds(ids);
            rolePermissionsVO.setPermissions(permissions);
            rolePermissionsVOS.add(rolePermissionsVO);
        });
        return new PageInfo<>(rolePermissionsVOS);
    }

    @Override
    public Role queryByRoleId(String roleId) {
        return roleDao.queryById(roleId);
    }

    @Override
    public Integer update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public Role save(Role role) {
        role.setId(Utils.generateID());
        roleDao.insert(role);

        return role;
    }

    @Override
    public Integer delete(String roleId) {
        return roleDao.deleteByRoleId(roleId);
    }

    @Override
    public RolePermissionsVO queryRoleAndPermissionByRoleId(String roleId) {

        Role role = roleDao.queryById(roleId);
        String permissionIds = role.getPermissionIds();
        String[] ids = permissionIds.split(",");
        List<Permission> permissions = permissionDao.queryPermissionByIds(ids);

        RolePermissionsVO rolePermissionsVO = new RolePermissionsVO();
        rolePermissionsVO.setRole(role);
        rolePermissionsVO.setPermissions(permissions);

        return rolePermissionsVO;
    }
}
