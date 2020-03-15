package com.kazma233.blog.dao.user;

import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.vo.UserPasswordUpdate;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    @Select("SELECT * FROM user_table WHERE username = #{username}")
    User queryByUsername(String username);

    @Update("update user_table set password = #{password} where id = #{uid}")
    void updatePassword(UserPasswordUpdate userRoleUpdate);
}
