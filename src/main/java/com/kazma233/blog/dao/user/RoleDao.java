package com.kazma233.blog.dao.user;

import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.vo.RoleQueryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {

    List<Role> queryAll(RoleQueryVO role);

    @Select("select id, role_name, permission_ids, description, create_time " +
            "from blog_role_table where id = #{roleId}")
    Role queryById(String roleId);

    @Select("SELECT brt.id, role_name, permission_ids, description, brt.create_time " +
            "FROM blog_role_table brt JOIN user_table ut ON brt.id = ut.role_id " +
            "WHERE ut.id = #{uid}")
    Role queryRoleByUid(String uid);

    void insert(Role role);

    void update(Role role);

    @Delete("DELETE FROM blog_role_table WHERE id = #{roleId}")
    void deleteByRoleId(String roleId);

}
