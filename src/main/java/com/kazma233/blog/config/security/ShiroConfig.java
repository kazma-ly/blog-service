package com.kazma233.blog.config.security;

import com.kazma233.blog.middleware.UserRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * shiro config
 * EnableAspectJAutoProxy: 注解设置权限 开启Aop类代理
 */
@Configuration
@EnableAspectJAutoProxy
public class ShiroConfig {

    // 拦截器的bean
//    @Bean
//    ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
//
//        shiroFilterFactoryBean.setLoginUrl("/findByUsernameAndPassword");
//        shiroFilterFactoryBean.setSuccessUrl("/home");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
//
//        Map<String, String> filterMap = new HashMap<>();
//        filterMap.put("/manage/**", "roles[admin,manage]");
//        filterMap.put("/**", "anon");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
//
//        return shiroFilterFactoryBean;
//    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        return shiroFilterFactoryBean;
    }

    /**
     * session 管理器
     */
    @Bean
    DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();

        defaultWebSessionManager.setSessionIdCookie(new SimpleCookie("SHIRO_SID"));
        // session 的失效时间 30分钟 milliseconds 1000 * 60 * 30
        defaultWebSessionManager.setGlobalSessionTimeout(1000 * 60 * 30);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        // 定时清理失效的会话 milliseconds
        defaultWebSessionManager.setSessionValidationInterval(1000 * 60 * 30);
        return defaultWebSessionManager;
    }

    // shiro注解的开启
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        return advisor;
    }

    // securityManager安全管理器
    @Bean
    DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm, DefaultWebSessionManager defaultWebSessionManager, CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(userRealm);
        // 设置session 管理器
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        return defaultWebSecurityManager;
    }

    // 自定义Realm
    @Bean
    UserRealm userRealm(EhCacheManager ehCacheManager) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(ehCacheManager);
        userRealm.setCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthorizationCachingEnabled(true);
        return userRealm;
    }

    // 缓存管理 会提供CacheManager
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        Resource resource = new ClassPathResource("ehcache.xml");
        cacheManagerFactoryBean.setConfigLocation(resource);
        return cacheManagerFactoryBean;
    }

    // 认证缓存管理
    @Bean
    public EhCacheManager ehCacheManager(CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    @Bean
    CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(new SimpleCookie("SHRIO_REM"));
        return cookieRememberMeManager;
    }


}
