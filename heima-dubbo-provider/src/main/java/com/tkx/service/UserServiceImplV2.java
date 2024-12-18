package com.tkx.service;

import com.tkx.pojo.User;
import org.apache.dubbo.config.annotation.Service;

import java.util.Date;

/**
 * @Author tkx
 * @Date 2024 12 07 21 11
 **/

// 超时时间为1000,重试次数为0次
@Service(timeout = 1000,retries = 2,version = "v2.0")
public class UserServiceImplV2 implements UserService{
    @Override
    public String sayHello() {
        return "hello,dubbo";
    }

    @Override
    public User query(Integer id) {
        System.out.println("v2.0被调用");
        // User user = new User(2, "tkx", "男",new Date(),"深圳");
        User user = new User();
        return user;
    }
}
