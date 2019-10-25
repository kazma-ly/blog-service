package com.kazma233.blog.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.config.properties.MyConfig;
import com.kazma233.blog.cons.DefaultConstant;
import com.kazma233.blog.dao.mongo.MongoFileDao;
import com.kazma233.blog.dao.user.RoleDao;
import com.kazma233.blog.dao.user.UserDao;
import com.kazma233.blog.dao.user.UserInfoDao;
import com.kazma233.blog.entity.log.MongoFile;
import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.UserInfo;
import com.kazma233.blog.entity.user.enums.NoticeStatus;
import com.kazma233.blog.entity.user.enums.UserStatus;
import com.kazma233.blog.entity.user.exception.UserException;
import com.kazma233.blog.entity.user.vo.*;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.ShiroUtils;
import com.kazma233.common.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements IUserService {

    private UserDao userDao;
    private MongoFileDao mongoFileDao;
    private RoleDao roleDao;
    private UserInfoDao userInfoDao;
    private MyConfig myConfig;

    @Override
    public User login(UserLogin userLogin) {

        User dbUser = userDao.queryByUsername(userLogin.getUsername());
        if (dbUser == null || StringUtils.isBlank(dbUser.getId())) {
            throw new UserException(Status.USER_NOT_FOUND_ERROR);
        }

        String inputPw = userLogin.getPassword();
        checkUserPw(inputPw, dbUser.getPassword());

        dbUser.setPassword(inputPw);
        return dbUser;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public String register(UserRegister userRegister) {
        String uid = Utils.generateID();

        User user = User.builder().
                id(uid).
                username(userRegister.getUsername()).
                password(encodePw(userRegister.getPassword())).
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
                id(Utils.generateID()).
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
        userInfo.setUid(ShiroUtils.getUid());
        userInfoDao.update(userInfo);
    }

    @Override
    public void updatePassword(UserPasswordUpdate userPasswordUpdate) {
        this.login(
                UserLogin.builder().username(userPasswordUpdate.getUsername()).password(userPasswordUpdate.getPassword()).build()
        );

        userPasswordUpdate.setPassword(encodePw(userPasswordUpdate.getNewPasswrod()));
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
        return userInfoDao.findOne(ShiroUtils.getUid());
    }

    @Override
    public void saveAvatar(MultipartFile avatarFile) {
        String uid = ShiroUtils.getUid();
        Optional<MongoFile> avatarMongoFile = mongoFileDao.findMongoFileByUid(uid);
        try (ByteArrayOutputStream avatarOutputStream = new ByteArrayOutputStream()) {
            clipAvatarFile(avatarFile, avatarOutputStream);

            MongoFile newAvatarMongoFile = MongoFile.builder().
                    uid(uid).
                    name(avatarFile.getOriginalFilename()).
                    contentType(avatarFile.getContentType()).
                    content(avatarOutputStream.toByteArray()).
                    build();
            if (avatarMongoFile.isPresent()) {
                mongoFileDao.deleteMongoFileByUid(uid);
            } else {
                userInfoDao.update(UserInfo.builder().uid(uid).avatar(myConfig.getUrlPre() + "/user/avatar/" + uid).build());
            }

            mongoFileDao.save(newAvatarMongoFile);
        } catch (IOException e) {
            log.error("保存头像失败", e);
            throw new UserException(Status.FAIL);
        }
    }

    @Override
    public MongoFile getAvatarMongoFile(String uid) {
        return mongoFileDao.findMongoFileByUid(uid).orElse(new MongoFile());
    }

    private void clipAvatarFile(MultipartFile multipartFile, OutputStream outputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        int size = Math.min(bufferedImage.getWidth(), bufferedImage.getHeight());
        Thumbnails.of(multipartFile.getInputStream()).
                sourceRegion(Positions.CENTER, size, size).
                scale(1).
                toOutputStream(outputStream);
    }

    private static String encodePw(String originPw) {
        byte[] newPwByte = originPw.getBytes(StandardCharsets.UTF_8);
        return DigestUtils.sha256Hex(newPwByte);
    }

    private static void checkUserPw(String inputPw, String dbPw) {
        if (!encodePw(inputPw).equals(dbPw)) {
            throw new UserException(Status.LOGIN_ERROR);
        }
    }
}
