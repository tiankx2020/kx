package com.tkx.demo.config;

import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author tkx
 * @Date 2024 12 17 23 11
 **/
@Configuration
public class ShiroConfig {

    @Bean
    public IniRealm getIniRealm(){
        IniRealm iniRealm=new IniRealm("classpath:shiro.ini");
        return iniRealm;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(IniRealm iniRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //securityManager要完成校验，需要realm
        securityManager.setRealm(iniRealm);
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean filter=new ShiroFilterFactoryBean();
        filter.setSecurityManager(defaultWebSecurityManager);
        //设置shiro的拦截规则
        //anon 匿名用户可访问   authc  认证用户可访问
        //user 使用RemeberMe的用户可访问  perms  对应权限可访问
        //role  对应的角色可访问
        Map<String,String> filterMap=new HashMap<>();
        filterMap.put("/","anon");
        filterMap.put("/login.html","anon");
        filterMap.put("/register.html","anon");
        filterMap.put("/user/login","anon");
        filterMap.put("/user/register","anon");
        filterMap.put("/static/**","anon");
        filterMap.put("/**","authc");
        filter.setFilterChainDefinitionMap(filterMap);

        filter.setLoginUrl("/login.html");
        //设置未授权页面跳转到登录页面
        filter.setUnauthorizedUrl("/login.html");
        return filter;
    }
}
