package com.kazma233.blog.service.user.impl;

import com.google.common.base.Strings;
import com.kazma233.blog.dao.user.UserDao;
import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.enums.UserStatus;
import com.kazma233.blog.entity.user.exception.UserException;
import com.kazma233.blog.entity.user.vo.UserJwtVO;
import com.kazma233.blog.entity.user.vo.UserLogin;
import com.kazma233.blog.entity.user.vo.UserPasswordUpdate;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.UserUtils;
import com.kazma233.blog.utils.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements IUserService {

    private UserDao userDao;

    @Override
    public String login(UserLogin userLogin) {

        User dbUser = userDao.queryByUsername(userLogin.getUsername());
        if (dbUser == null || Strings.isNullOrEmpty(dbUser.getId())) {
            throw new UserException(Status.USER_NOT_FOUND_ERROR);
        }

        if (!UserStatus.ENABLE.getCode().equals(dbUser.getEnable())) {
            throw new UserException(Status.USER_DISABLED);
        }

        String inputPw = userLogin.getPassword();
        UserUtils.checkUserPw(inputPw, dbUser.getPassword());

        return JwtUtils.getLoginToken(UserJwtVO.builder().id(dbUser.getId()).username(dbUser.getUsername()).build());
    }

    @Override
    public void updatePassword(UserPasswordUpdate userPasswordUpdate) {
        this.login(
                UserLogin.builder().username(userPasswordUpdate.getUsername()).password(userPasswordUpdate.getPassword()).build()
        );

        userPasswordUpdate.setPassword(UserUtils.encodePw(userPasswordUpdate.getNewPasswrod()));
        userDao.updatePassword(userPasswordUpdate);
    }

}
