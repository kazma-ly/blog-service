package com.kazma233.blog.service.user;

import com.kazma233.blog.entity.user.vo.UserLogin;
import com.kazma233.blog.entity.user.vo.UserPasswordUpdate;

public interface IUserService {

    String login(UserLogin userLogin);

    void updatePassword(UserPasswordUpdate userPasswordUpdate);

}
