package com.kazma233.blog.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.dao.mongo.MongoFileDao;
import com.kazma233.blog.dao.user.RoleDao;
import com.kazma233.blog.dao.user.UserDao;
import com.kazma233.blog.entity.mongo.MongoFile;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.enums.user.UserStatus;
import com.kazma233.blog.exception.UserException;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.vo.user.UserChangePwVO;
import com.kazma233.blog.vo.user.UserQueryVO;
import com.kazma233.blog.vo.user.UserRoleVO;
import com.kazma233.common.Utils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MongoFileDao mongoFileDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public User login(User user) {
        // 从数据库通过用户名查询该用户
        User encodeUser = userDao.queryByUsername(user.getUsername());
        if (encodeUser == null || StringUtils.isBlank(encodeUser.getId())) {
            throw new UserException(ResultEnums.UNKNOW_USER_ERROR);
        }
        // 验证加密的密码是否相等
        String userInputPw = user.getPassword();
        byte[] userInputPwBytes = userInputPw.getBytes(Charset.forName("UTF-8"));
        String userInputEncodePwStr = DigestUtils.sha256Hex(userInputPwBytes);
        if (!userInputEncodePwStr.equals(encodeUser.getPassword())) {
            throw new UserException(ResultEnums.LOGIN_FAIL);
        }
        // 密码shiro需要使用
        encodeUser.setPassword(userInputPw);
        return encodeUser;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public String register(User user) {
        String uid = Utils.generateID();

        String inputPassword = user.getPassword();
        byte[] inputPasswordByte = inputPassword.getBytes(Charset.forName("UTF-8"));

        user.setId(uid);
        user.setPassword(DigestUtils.sha256Hex(inputPasswordByte));
        user.setEnable(UserStatus.ENABLE.getCode());

        if ("admin".equals(user.getUsername())) {
            user.setRoleId("1000");
        } else {
            // 全部都是普通用户
            user.setRoleId("1001");
        }
        userDao.insert(user);
        return uid;
    }

    @Override
    public User updateRole(User user) {
        User updateRoleUser = new User();
        updateRoleUser.setId(user.getId());
        updateRoleUser.setRoleId(user.getRoleId());

        int res = userDao.update(updateRoleUser);
        if (res != 1) {
            throw new UserException(ResultEnums.FAIL);
        }
        return userDao.queryById(user.getId());
    }

    @Override
    public int insertAvatar(String uid, MultipartFile multipartFile) {
        Optional<MongoFile> mongoFile = mongoFileDao.findById("avatar_" + uid);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            MongoFile mf = new MongoFile();
            mf.setId("avatar_" + uid);
            mf.setName(multipartFile.getOriginalFilename());
            mf.setContentType(multipartFile.getContentType());
            clipAvatarFile(multipartFile, outputStream);
            mf.setContent(multipartFile.getBytes());
            if (!mongoFile.isPresent()) {
                mongoFileDao.save(mf);
            } else {
                mongoFileDao.insert(mf);
            }
        } catch (IOException e) {
            throw new UserException("头像文件保存失败");
        }
        return 1;
    }

    @Override
    public MongoFile queryAvatar(String uid, boolean isClip) {
        if (isClip) {
            Optional<MongoFile> mongoFileOptional = mongoFileDao.findById("uid_" + uid + "_thum");
            if (mongoFileOptional.isPresent()) {
                return mongoFileOptional.get();
            }
        } else {
            // 两种写法都试试
//            MongoFile mongoFile = new MongoFile();
//            mongoFile.setId("uid_" + uid);
//            return mongoFileDao.findOne(Example.of(mongoFile)).get();
            Optional<MongoFile> mongoFileOptional = mongoFileDao.findById("uid_" + uid);
            if (mongoFileOptional.isPresent()) {
                return mongoFileOptional.get();
            }
        }
        return new MongoFile();
    }

    @Override
    public int updatePassword(UserChangePwVO user) {
        // 验证用户
        User checkUser = new User();
        checkUser.setUsername(user.getUsername());
        checkUser.setPassword(getEncodePw(user.getPassword()));
        User loginUser = userDao.findByUsernameAndPassword(checkUser);
        if (loginUser == null || StringUtils.isBlank(loginUser.getId())) {
            throw new UserException(ResultEnums.CHECK_USER_FAIL);
        }

        // 修改密码
        User updateUser = new User();
        updateUser.setId(loginUser.getId());
        updateUser.setPassword(getEncodePw(user.getNewPasswrod()));
        return userDao.update(updateUser);
    }

    @Override
    public PageInfo<UserRoleVO> queryUser(UserQueryVO userQueryVO) {
        PageHelper.startPage(userQueryVO.getPage(), userQueryVO.getCount());
        List<UserRoleVO> users = userDao.queryAllUserByArgs(userQueryVO);
        return new PageInfo<>(users);
    }

    @Override
    public Role queryRoleByUid(String uid) {
        return roleDao.queryRoleByUid(uid);
    }

    private void clipAvatarFile(MultipartFile multipartFile, ByteArrayOutputStream outputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int size = width > height ? height : width;
        Thumbnails.
                of(multipartFile.getInputStream()).
                sourceRegion(Positions.CENTER, size, size).
                scale(1).
                toOutputStream(outputStream);

    }

    private String getEncodePw(String originPw) {
        byte[] newPwByte = originPw.getBytes(Charset.forName("UTF-8"));
        return DigestUtils.sha256Hex(newPwByte);
    }

}
