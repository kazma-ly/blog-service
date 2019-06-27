package com.kazma233.blog.service.user;

import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.vo.user.RolePermissionsVO;
import com.kazma233.blog.vo.user.RoleQueryVO;
import com.github.pagehelper.PageInfo;

public interface IRoleService {

    PageInfo<RolePermissionsVO> queryAllArgs(RoleQueryVO roleQueryVO);

    Role queryByRoleId(String roleId);

    Integer update(Role role);

    Role save(Role role);

    Integer delete(String roleId);

    RolePermissionsVO queryRoleAndPermissionByRoleId(String roleId);

}
