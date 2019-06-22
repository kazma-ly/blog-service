package com.kazma233.blog.middleware;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * - -
 * Created by zly on 2017/5/2.
 */

public class LoginFormAuthticationFilter extends FormAuthenticationFilter {

    /**
     * 重写身份认证不成功方法
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        String errorMsg = null;
        if (e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
            errorMsg = "用户名或密码不正确";
        } else {
            errorMsg = "出现未知错误";
        }

        request.setAttribute("errorMsg", errorMsg);

        return true;
    }

    /**
     * 重写身份认证成功以后返回的地址
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {

        String successUrl = getSuccessUrl();
        WebUtils.issueRedirect(request, response, successUrl, null, true);
    }
}
