package com.kazma233.blog.service.user;

import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.vo.user.RolePermissionsVO;
import com.kazma233.blog.vo.user.RoleQueryVO;
import com.github.pagehelper.PageInfo;

public interface IRoleService {

    /**
     * @param roleQueryVO 查询参数
     */
    PageInfo<RolePermissionsVO> queryByPage(RoleQueryVO roleQueryVO);

    Role queryByRoleId(String roleId);

    Integer update(Role role);

    /**
     * 插入并且返回插入的对象
     */
    Role insert(Role role);

    Integer delete(String roleId);

    RolePermissionsVO queryRoleAndPermissionByRoleId(String roleId);

}
