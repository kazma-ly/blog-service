package com.kazma233.blog.service.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.user.MongoFile;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.vo.*;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

    User login(UserLogin userLogin);

    String register(UserRegister userRegister);

    void updateUserInfo(UserInfo userInfo);

    void updatePassword(UserPasswordUpdate userPasswordUpdate);

    UserInfo getUserInfo();

    void saveAvatar(MultipartFile avatarFile);

    MongoFile getAvatarMongoFile(String uid);

    Role queryRoleByUid(String uid);

    PageInfo<UserRoleVO> queryUser(UserQuery userQuery);

    void updateRole(UserRoleUpdate userRoleUpdate);
}
