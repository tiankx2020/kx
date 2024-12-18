package com.tkx.service;

import com.tkx.pojo.User;

/**
 * @Author tkx
 * @Date 2024 12 08 18 03
 **/
public interface UserService {
    public User query(Integer id);
    public String sayHello();
}
