package com.kazma233.blog.service.user;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IUserService {

    String login(UserLogin userLogin);

    String register(UserRegister userRegister);

    void updateUserInfo(UserInfo userInfo);

    void updatePassword(UserPasswordUpdate userPasswordUpdate);

    UserInfo getUserInfo();

    void saveAvatar(MultipartFile avatarFile) throws IOException;

    void getAvatar(String id, HttpServletResponse response) throws IOException;

    Role queryRoleByUid(String uid);

    PageInfo<UserRoleVO> queryUser(UserQuery userQuery);

    void updateRole(UserRoleUpdate userRoleUpdate);
}
