package com.kazma233.blog.service.user;

import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.vo.RolePermissionsVO;
import com.kazma233.blog.entity.user.vo.RoleQueryVO;
import com.github.pagehelper.PageInfo;

public interface IRoleService {

    PageInfo<RolePermissionsVO> queryAllArgs(RoleQueryVO roleQueryVO);

    Role queryByRoleId(String roleId);

    void update(Role role);

    void save(Role role);

    void delete(String roleId);

    RolePermissionsVO queryRoleAndPermissionByRoleId(String roleId);

}
