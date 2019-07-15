package com.kazma233.blog.dao.user;

import com.kazma233.blog.entity.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao {

    @Insert("insert into user_info (id, uid, notice_email, nickname, notice_status, create_time, update_time, avatar, description, personal_link) " +
            "values (#{id}, #{uid}, #{noticeEmail}, #{nickname}, #{noticeStatus}, now(), now(), #{avatar}, #{description}, #{personalLink})")
    void save(UserInfo userInfo);

    void update(UserInfo userInfo);

    @Select("select * from user_info where uid = #{uid}")
    UserInfo findOne(String uid);
}
