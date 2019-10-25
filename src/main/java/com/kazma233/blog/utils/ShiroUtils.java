package com.kazma233.blog.utils;

import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.user.User;
import com.kazma233.blog.entity.user.exception.UserException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {

    private static final String USER_KEY = "USER_KEY";

    public static void setUser(User user) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(USER_KEY, user);
    }

    public static User getUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object userObj = subject.getSession().getAttribute(USER_KEY);
            if (userObj instanceof User) {
                return (User) userObj;
            }
        }

        return null;
    }

    public static String getUid() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object userObj = subject.getSession().getAttribute(USER_KEY);
            if (userObj instanceof User) {
                return ((User) userObj).getId();
            }
        }

        throw new UserException(Status.UN_AUTH_ERROR);
    }

    public static String getUidNotMust() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object userObj = subject.getSession().getAttribute(USER_KEY);
            if (userObj instanceof User) {
                return ((User) userObj).getId();
            }
        }

        return null;
    }

}
