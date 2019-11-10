package com.kazma233.blog.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.kazma233.blog.cons.DefaultConstant;
import com.kazma233.blog.dao.user.RoleDao;
import com.kazma233.blog.dao.user.UserDao;
import com.kazma233.blog.dao.user.UserInfoDao;
import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.enums.NoticeStatus;
import com.kazma233.blog.entity.user.enums.UserStatus;
import com.kazma233.blog.entity.user.exception.UserException;
import com.kazma233.blog.entity.user.vo.*;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.UserUtils;
import com.kazma233.blog.utils.id.IDGenerater;
import com.kazma233.blog.utils.jwt.JwtUtils;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements IUserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private UserInfoDao userInfoDao;
    private GridFsTemplate gridFsTemplate;

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

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public String register(UserRegister userRegister) {
        String uid = IDGenerater.generateID();

        User user = User.builder().
                id(uid).
                username(userRegister.getUsername()).
                password(UserUtils.encodePw(userRegister.getPassword())).
                enable(UserStatus.ENABLE.getCode()).
                build();

        if (DefaultConstant.ADMIN_NAME.equals(user.getUsername())) {
            user.setRoleId(DefaultConstant.ADMIN_ROLE);
        } else {
            user.setRoleId(DefaultConstant.NORMAL_ROLE);
        }

        userDao.insert(user);
        userInfoDao.save(UserInfo.builder().
                uid(uid).
                id(IDGenerater.generateID()).
                noticeStatus(NoticeStatus.DISABLE.getCode()).
                nickname(user.getUsername()).
                build());

        return uid;
    }

    @Override
    public void updateRole(UserRoleUpdate userRoleUpdate) {
        userDao.updateRole(userRoleUpdate);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfo.setUid(UserUtils.getUserId());
        userInfoDao.update(userInfo);
    }

    @Override
    public void updatePassword(UserPasswordUpdate userPasswordUpdate) {
        this.login(
                UserLogin.builder().username(userPasswordUpdate.getUsername()).password(userPasswordUpdate.getPassword()).build()
        );

        userPasswordUpdate.setPassword(UserUtils.encodePw(userPasswordUpdate.getNewPasswrod()));
        userDao.updatePassword(userPasswordUpdate);
    }

    @Override
    public PageInfo<UserRoleVO> queryUser(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPageNo(), userQuery.getPageSize());
        List<UserRoleVO> users = userDao.queryAllUserByArgs(userQuery);

        return new PageInfo<>(users);
    }

    @Override
    public Role queryRoleByUid(String uid) {
        return roleDao.queryRoleByUid(uid);
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfoDao.findOne(UserUtils.getUserId());
    }

    @Override
    public void saveAvatar(MultipartFile avatarFile) throws IOException {
        String uid = UserUtils.getUserId();

        ObjectId storeId = gridFsTemplate.store(
                avatarFile.getInputStream(),
                avatarFile.getOriginalFilename(),
                avatarFile.getContentType(),
                Map.of(DefaultConstant.MONGO_MATADATA_UID, uid, DefaultConstant.MONGO_MATADATA_TYPE, DefaultConstant.MONGO_MATADATA_TYPE_AVATAR)
        );

        userInfoDao.update(
                UserInfo.builder().uid(uid).avatar(storeId.toString()).build()
        );
    }

    @Override
    public void getAvatar(String id, HttpServletResponse response) throws IOException {
        GridFSFile gridFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (gridFile == null) {
            return;
        }
        GridFsResource gridResponse = gridFsTemplate.getResource(gridFile);

        ByteStreams.copy(gridResponse.getInputStream(), response.getOutputStream());
    }

}
