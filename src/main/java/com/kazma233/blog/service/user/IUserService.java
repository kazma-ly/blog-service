package com.kazma233.blog.service.user;

import com.kazma233.blog.entity.mongo.MongoFile;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.vo.user.UserChangePwVO;
import com.kazma233.blog.vo.user.UserQueryVO;
import com.kazma233.blog.vo.user.UserRoleVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户Service类
 * Created by zly.private on 16/7/28.
 *
 * @author kazma
 */
public interface IUserService {

    /**
     * 登录
     *
     * @return 返回用户
     */
    User doLoin(User user);

    /**
     * 注册
     */
    String doRegister(User user);

    /**
     * 修改权限
     */
    User updateRole(User user);

    // 插入用户头像
    int insertUserAvatar(String uid, MultipartFile multipartFile);

    // 获取用户头像
    MongoFile queryUserAvatar(String uid, boolean isClip);

    /**
     * 更新用户密码
     */
    int updatePassword(UserChangePwVO user);

    /**
     * 查询用户
     */
    PageInfo<UserRoleVO> queryUser(UserQueryVO userQueryVO);

    Role queryRoleByUid(String uid);
}
