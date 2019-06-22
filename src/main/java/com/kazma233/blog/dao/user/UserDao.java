package com.kazma233.blog.dao.user;


import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.vo.user.UserQueryVO;
import com.kazma233.blog.vo.user.UserRoleVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from user_table WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(User user);

    @Select("SELECT * FROM user_table WHERE username = #{username}")
    User queryByUsername(String username);

    @Select("SELECT * from user_table WHERE id = #{id}")
    User queryById(String id);

    int insert(User user);

    int update(User user);

    List<UserRoleVO> queryAllUserByArgs(UserQueryVO userQueryVO);
}
