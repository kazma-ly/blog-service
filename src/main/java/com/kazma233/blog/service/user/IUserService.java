package com.kazma233.blog.service.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.log.MongoFile;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.vo.*;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

    User login(UserLogin userLogin);

    String register(UserRegister userRegister);

    void updateRole(UserRoleUpdate userRoleUpdate);

    void updateUserInfo(UserInfo userInfo);

    void updatePassword(UserPasswordUpdate userPasswordUpdate);

    PageInfo<UserRoleVO> queryUser(UserQuery userQuery);

    Role queryRoleByUid(String uid);

    UserInfo getUserInfo();

    void saveAvatar(MultipartFile avatarFile);

    MongoFile getAvatarMongoFile(String uid);

}
