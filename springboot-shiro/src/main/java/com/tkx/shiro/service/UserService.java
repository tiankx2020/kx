package com.tkx.shiro.service;

import com.tkx.shiro.entity.User;

/**
 * @Author tkx
 * @Date 2025 01 16 00 00
 **/
public interface UserService{
    //用户登录
    User getUserInfoByName(String name);
}
