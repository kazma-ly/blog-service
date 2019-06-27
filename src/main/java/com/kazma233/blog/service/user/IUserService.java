package com.kazma233.blog.service.user;

import com.kazma233.blog.entity.mongo.MongoFile;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.vo.user.UserChangePwVO;
import com.kazma233.blog.vo.user.UserQueryVO;
import com.kazma233.blog.vo.user.UserRoleVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

    User login(User user);

    String register(User user);

    User updateRole(User user);

    int insertAvatar(String uid, MultipartFile multipartFile);

    MongoFile queryAvatar(String uid, boolean isClip);

    int updatePassword(UserChangePwVO user);

    PageInfo<UserRoleVO> queryUser(UserQueryVO userQueryVO);

    Role queryRoleByUid(String uid);
}
