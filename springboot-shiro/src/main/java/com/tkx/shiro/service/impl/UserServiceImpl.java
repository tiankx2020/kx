package com.tkx.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkx.shiro.entity.User;
import com.tkx.shiro.mapper.UserMapper;
import com.tkx.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author tkx
 * @Date 2025 01 16 00 01
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfoByName(String name){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public List<String> getUserRoleInfo(String principal) {
        return userMapper.getUserRoleInfoMapper(principal);
    }

    @Override
    public List<String> getUserPermissionInfo(List<String> roles) {
        return userMapper.getUserPermissionInfoMapper(roles);
    }
}
