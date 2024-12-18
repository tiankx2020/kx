package com.tkx.controller;

import com.tkx.pojo.User;
import com.tkx.service.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Author tkx
 * @Date 2024 12 08 22 45
 **/
@RestController
@RequestMapping("/tkx")
public class UserController {

    @Autowired
    private UserInterface userInterface;

    // @PostMapping("getUser")
    // public User getUser(){
    //     User user = new User(2,"zzz","男",new Date(),"深圳");
    //     return user;
    // }

    @PostMapping("/getList")
    public List<User> getList(){
        return userInterface.getList();
    }
}
