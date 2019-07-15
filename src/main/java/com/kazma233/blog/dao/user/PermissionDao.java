package com.kazma233.blog.dao.user;

import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.entity.user.vo.PermissionQueryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    List<Permission> queryPermissionByIds(@Param("ids") String[] ids);

    List<Permission> queryPermissionByCondition(PermissionQueryVO permissionQueryVO);

    @Select("select id, permission_name, permission_description, create_time from blog_permission_table")
    List<Permission> queryAll();

    void insert(Permission permission);

    void updateById(Permission permission);

    @Delete("delete from blog_permission_table where id = #{id}")
    void deleteById(String id);

    void deleteByIds(List<String> ids);

}
