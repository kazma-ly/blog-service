package com.kazma233.blog.service.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.log.MongoFile;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.vo.UserChangePwVO;
import com.kazma233.blog.entity.user.vo.UserQueryVO;
import com.kazma233.blog.entity.user.vo.UserRoleVO;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

    User login(User user);

    String register(User user);

    void updateRole(User user);

    void updateUserInfo(UserInfo userInfo);

    void updatePassword(UserChangePwVO user);

    PageInfo<UserRoleVO> queryUser(UserQueryVO userQueryVO);

    Role queryRoleByUid(String uid);

    UserInfo getUserInfo();

    void saveAvatar(MultipartFile avatarFile);

    MongoFile getAvatarMongoFile(String uid);

}
