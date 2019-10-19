package com.kazma233.blog.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.dao.user.PermissionDao;
import com.kazma233.blog.dao.user.RoleDao;
import com.kazma233.blog.entity.permission.Permission;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.role.vo.RoleAdd;
import com.kazma233.blog.entity.role.vo.RolePermissionsVO;
import com.kazma233.blog.entity.role.vo.RoleQuery;
import com.kazma233.blog.entity.role.vo.RoleUpdate;
import com.kazma233.blog.service.user.IRoleService;
import com.kazma233.common.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class RoleService implements IRoleService {

    private RoleDao roleDao;
    private PermissionDao permissionDao;

    @Override
    public PageInfo<RolePermissionsVO> queryAllArgs(RoleQuery roleQueryVO) {
        PageHelper.startPage(roleQueryVO.getPageNo(), roleQueryVO.getPageSize());

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
    public void update(RoleUpdate roleUpdate) {
        roleDao.update(roleUpdate);
    }

    @Override
    public void save(RoleAdd roleAdd) {
        roleDao.insert(Role.builder().
                id(Utils.generateID()).
                createTime(new Date()).
                description(roleAdd.getDescription()).
                permissionIds(roleAdd.getPermissionIds()).
                roleName(roleAdd.getRoleName()).
                build()
        );
    }

    @Override
    public void delete(String roleId) {
        roleDao.deleteByRoleId(roleId);
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
