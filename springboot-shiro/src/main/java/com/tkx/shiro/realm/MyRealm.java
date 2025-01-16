package com.tkx.shiro.realm;

import com.tkx.shiro.entity.User;
import com.tkx.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author tkx
 * @Date 2025 01 16 00 01
 **/
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 授权相关
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
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
