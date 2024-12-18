package com.tkx.demo.controller;

import com.tkx.demo.service.UserService_Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author tkx
 * @Date 2024 12 17 23 15
 **/
@Controller
@RequestMapping("/user")
public class User_Controller {


    @Autowired
    private UserService_Impl userService;

    @RequestMapping("/login")
    public String login(String username,String password){
        try {
            userService.checkLogin(username,password);
            return "index";
        }catch (Exception e){
            System.out.println("登录失败");
            return "login";
        }
    }
}
