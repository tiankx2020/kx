package com.tkx.demo.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @Author tkx
 * @Date 2024 12 17 23 14
 **/
@Service
public class UserService_Impl {
    public void checkLogin(String username,String Password) throws Exception{
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,Password);
        subject.login(token);
    }

}
