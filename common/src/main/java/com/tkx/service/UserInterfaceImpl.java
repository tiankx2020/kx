package com.tkx.service;

import com.tkx.mapper.UserMapper;
import com.tkx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author tkx
 * @Date 2024 12 09 00 52
 **/
@Component
public class UserInterfaceImpl implements UserInterface{

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getList() {
        return userMapper.getList();
    }
}
