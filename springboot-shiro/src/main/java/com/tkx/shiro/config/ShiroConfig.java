package com.tkx.shiro.config;

import com.tkx.shiro.realm.MyRealm;
import com.tkx.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tkx
 * @Date 2025 01 16 00 06
 **/
@Configuration
@Slf4j
public class ShiroConfig {


    @Autowired
    private MyRealm myRealm;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        //1. 创建defaultWebSecurityManager对象
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //2. 创建加密对象
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        //3. 将加密对象存储到myRealm
        myRealm.setCredentialsMatcher(matcher);

        manager.setRealm(myRealm);
        return manager;
    }

    //配置Shiro内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
//        设置不认证就可以访问的资源
        definition.addPathDefinition("/myController/userLogin", "anon");
        definition.addPathDefinition("/myController/login", "anon");
        //配置登出过滤器
        definition.addPathDefinition("/logout","logout");
//        设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**", "authc");
        return definition;
    }


    // cookie 属性设置
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置跨域
        // cookie.setDomain("domain");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        return cookie;
    }

    //创建 Shiro 的 cookie 管理对象
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }


}
