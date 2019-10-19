package com.kazma233.blog.config.security;

import com.kazma233.blog.entity.permission.Permission;
import com.kazma233.blog.entity.role.Role;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.enums.UserStatus;
import com.kazma233.blog.entity.user.vo.UserLogin;
import com.kazma233.blog.service.user.IPermissionService;
import com.kazma233.blog.service.user.IUserService;
import com.kazma233.blog.utils.ShiroUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class MyAuthorizingRealm extends AuthorizingRealm {

    private IUserService userService;
    private IPermissionService permissionService;

    /**
     * 认证
     * 验证当前登录的Subject中执行Subject.findByUsernameAndPassword()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 从传入的token (UsernamePasswordToken) 获取我们的身份信息(userName)
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User dbUser = userService.login(
                UserLogin.builder().username(token.getUsername()).password(new String(token.getPassword())).build()
        );

        if (dbUser != null && UserStatus.ENABLE.getCode().equals(dbUser.getEnable())) {
            ShiroUtils.setUser(dbUser);

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
            permissions.forEach(
                    permission -> simpleAuthorizationInfo.addStringPermission(permission.getPermissionName())
            );
        }

        return simpleAuthorizationInfo;
    }
}