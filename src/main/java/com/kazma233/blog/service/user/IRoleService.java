package com.kazma233.blog.service.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.role.vo.RoleAdd;
import com.kazma233.blog.entity.role.vo.RolePermissionsVO;
import com.kazma233.blog.entity.role.vo.RoleQuery;
import com.kazma233.blog.entity.role.vo.RoleUpdate;

public interface IRoleService {

    PageInfo<RolePermissionsVO> queryAllArgs(RoleQuery roleQueryVO);

    Role queryByRoleId(String roleId);

    void update(RoleUpdate roleUpdate);

    void save(RoleAdd roleAdd);

    void delete(String roleId);

    RolePermissionsVO queryRoleAndPermissionByRoleId(String roleId);

}
