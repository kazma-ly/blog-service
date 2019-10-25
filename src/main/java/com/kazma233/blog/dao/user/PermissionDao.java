package com.kazma233.blog.dao.user;

import com.kazma233.blog.entity.permission.Permission;
import com.kazma233.blog.entity.permission.vo.PermissionQuery;
import com.kazma233.blog.entity.permission.vo.PermissionUpdate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    List<Permission> queryPermissionByIds(@Param("ids") String[] ids);

    List<Permission> queryPermissionByCondition(PermissionQuery permissionQuery);

    void insert(Permission permission);

    void updateById(PermissionUpdate permissionUpdate);

    @Delete("delete from blog_permission_table where id = #{id}")
    void deleteById(String id);

}
