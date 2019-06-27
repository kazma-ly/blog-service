package com.kazma233.blog.dao.user;

import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.vo.user.PermissionQueryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    public List<Permission> queryPermissionByIds(@Param("ids") String[] ids);

    public List<Permission> queryPermissionByCondition(PermissionQueryVO permissionQueryVO);

    @Select("select id, permission_name, permission_description, create_time from blog_permission_table")
    public List<Permission> queryAll();

    public Integer insert(Permission permission);

    public Integer updateById(Permission permission);

    @Delete("delete from blog_permission_table where id = #{id}")
    public Integer deleteById(String id);

    public Integer deleteByIds(List<String> ids);

}
