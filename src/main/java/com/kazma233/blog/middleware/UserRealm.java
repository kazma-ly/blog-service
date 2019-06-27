package com.kazma233.blog.middleware;

import com.kazma233.blog.entity.user.Permission;
import com.kazma233.blog.entity.user.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.enums.user.UserStatus;
import com.kazma233.blog.service.user.IPermissionService;
import com.kazma233.blog.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Shiro 自定义Realm
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    /**
     * 认证
     * 验证当前登录的Subject中执行Subject.findByUsernameAndPassword()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 从传入的token (UsernamePasswordToken) 获取我们的身份信息(userName)
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = User.builder().
                username(token.getUsername()).
                password(new String(token.getPassword())).
                build();

        // 数据库查询
        User dbUser = userService.login(user);

        if (dbUser != null && UserStatus.ENABLE.getCode().equals(dbUser.getEnable())) {
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", dbUser);

            // 身份信息已经确认，接下来进行凭证信息匹配 也就下面这个方法
            // 身份信息确认以后，凭证信息的确认由SimpleAuthenticationInfo 的父类AuthenticationInfo进行验证
            return new SimpleAuthenticationInfo(dbUser.getId(), dbUser.getPassword(), getName());
        }
        return null;
    }

    /**
     * Subject授予角色和权限
     * 该方法的调用时机为需授权资源被访问时 可对认证进行缓存
     *
     * @param principalCollection 就是上面的SimpleAuthenticationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String uid = (String) principalCollection.getPrimaryPrincipal();

        Role role = userService.queryRoleByUid(uid);

        //将得到的权限信息放入simpleAuthorizationInfo对象保存
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        if (role != null) {
            String permissionIds = role.getPermissionIds();
            String[] ids = permissionIds.split(",");
            List<Permission> permissions = permissionService.queryByIds(ids);
            permissions.forEach(permission -> {
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
            });
        }
        return simpleAuthorizationInfo;
    }
}