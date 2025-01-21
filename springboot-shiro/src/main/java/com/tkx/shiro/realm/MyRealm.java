package com.tkx.shiro.realm;

import com.tkx.shiro.entity.User;
import com.tkx.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import static java.awt.SystemColor.info;

/**
 * @Author tkx
 * @Date 2025 01 16 00 01
 **/
@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 授权相关
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入自定义授权方法");
        //1 创建对象，存储当前登录的用户的权限和角色
        String principal = principalCollection.getPrimaryPrincipal().toString();
        // 调用接口方法获取用户的角色信息
        List<String> roles = userService.getUserRoleInfo(principal);
        log.info("当前用户角色信息:"+roles);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 创建对象,存储当前对象的权限和角色
        if(roles!=null && roles.size()>0){
            List<String> permissions = userService.getUserPermissionInfo(roles);
            log.info("当前用户的权限信息--->"+permissions);
            info.addStringPermissions(permissions);
        }
        info.addRoles(roles);
        return info;
    }


    // 认证相关
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //1.获取用户身份信息
        String name = authenticationToken.getPrincipal().toString();
        //2.调用业务层获取用户信息(数据库)
        User user = userService.getUserInfoByName(name);
        //3.非空判断，将数据封装返回
        if(user!=null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    // 用户名
                    authenticationToken.getPrincipal(),
                    // 数据库中的密码
                    user.getPwd(),
                    // 加盐
                    ByteSource.Util.bytes(""),
                    authenticationToken.getPrincipal().toString()
            );
            return info;
        }
        return null;
    }
}
