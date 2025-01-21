package com.tkx.shiro.service;

import com.tkx.shiro.entity.User;

import java.util.List;

/**
 * @Author tkx
 * @Date 2025 01 16 00 00
 **/
public interface UserService{
    //用户登录
    User getUserInfoByName(String name);

    public List<String> getUserRoleInfo(String principal);

    public List<String> getUserPermissionInfo(List<String> roles);
}
