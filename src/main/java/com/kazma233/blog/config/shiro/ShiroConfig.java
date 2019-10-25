package com.kazma233.blog.config.shiro;

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

@Configuration
// 注解设置权限 开启Aop类代理
@EnableAspectJAutoProxy
public class ShiroConfig {

    private static final int DAY_SECOND = 86400;
    private static final int THIRTY_MINUTE_MILLISECOND = 1800000;

    private static final String SHIRO_SID_NAME = "SHIRO_SID";
    private static final String SHIRO_REM_NAME = "SHIRO_REM";

    private static final String EHCACHE_CONFIG_PATH = "ehcache.xml";

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
        SimpleCookie shiroSid = new SimpleCookie(SHIRO_SID_NAME);
        shiroSid.setMaxAge(THIRTY_MINUTE_MILLISECOND);
        defaultWebSessionManager.setSessionIdCookie(shiroSid);
        // session 的失效时间
        defaultWebSessionManager.setGlobalSessionTimeout(THIRTY_MINUTE_MILLISECOND);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        // 定时清理失效的会话
        defaultWebSessionManager.setSessionValidationInterval(THIRTY_MINUTE_MILLISECOND);
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
    DefaultWebSecurityManager defaultWebSecurityManager(MyAuthorizingRealm myAuthorizingRealm, DefaultWebSessionManager defaultWebSessionManager, CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(myAuthorizingRealm);
        // 设置session 管理器
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        return defaultWebSecurityManager;
    }

    // 自定义Realm
    @Bean
    MyAuthorizingRealm userRealm(EhCacheManager ehCacheManager, MyAuthorizingRealm myAuthorizingRealm) {
        myAuthorizingRealm.setCacheManager(ehCacheManager);
        myAuthorizingRealm.setCachingEnabled(true);
        myAuthorizingRealm.setAuthenticationCachingEnabled(true);
        myAuthorizingRealm.setAuthorizationCachingEnabled(true);
        return myAuthorizingRealm;
    }

    // 缓存管理 会提供CacheManager
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        Resource resource = new ClassPathResource(EHCACHE_CONFIG_PATH);
        cacheManagerFactoryBean.setConfigLocation(resource);
        return cacheManagerFactoryBean;
    }

    // 认证缓存管理
    @Bean
    public EhCacheManager ehCacheManager(CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile(EHCACHE_CONFIG_PATH);
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    @Bean
    CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie shiro = new SimpleCookie(SHIRO_REM_NAME);
        shiro.setMaxAge(DAY_SECOND);
        cookieRememberMeManager.setCookie(shiro);
        return cookieRememberMeManager;
    }


}
