package com.tkx.shiro.config;

import com.tkx.shiro.realm.MyRealm;
import com.tkx.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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
        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
//        设置不认证就可以访问的资源
        defaultShiroFilterChainDefinition.addPathDefinition("/myController/userLogin", "anon");
        defaultShiroFilterChainDefinition.addPathDefinition("/login", "anon");
//        设置需要进行登录认证的拦截范围
        defaultShiroFilterChainDefinition.addPathDefinition("/**", "authc");
        return defaultShiroFilterChainDefinition;
    }


}
