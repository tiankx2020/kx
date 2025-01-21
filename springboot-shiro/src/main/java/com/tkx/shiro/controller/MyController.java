package com.tkx.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author tkx
 * @Date 2025 01 16 00 11
 **/
@Controller
@RequestMapping("/myController")
public class MyController {

    @GetMapping("/login")
    public String log() {
        return "login";
    }

    @GetMapping("userLogin")
    public String userLogin(String name, String pwd, HttpSession session, @RequestParam(defaultValue = "false")boolean rememberMe) {
        //1.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2.封装请求数据到token
        AuthenticationToken token = new UsernamePasswordToken(name, pwd, rememberMe);
        //3.调用login方法进行登录认证
        try {
            subject.login(token);
            //return "登录成功";
            session.setAttribute("user", token.getPrincipal().toString());
            return "main";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
            return "登录失败";
        }
    }

    //登录认证验证  rememberMe
    @GetMapping("userLoginRm")
    public String userLogin(HttpSession session) {
        session.setAttribute("user", "rememberMe");
        return "main";
    }


    //登录认证验证权限
    @RequiresPermissions("user:permissions")
    @GetMapping("userPermissions")
    @ResponseBody
    public String userLoginPermissions() {
        System.out.println("登录认证验证权限");
        return "验证权限成功";
    }

    @RequiresPermissions("user:login:roles")
    @GetMapping("userLoginRoles")
    @ResponseBody
    public String userLoginRoles() {
        System.out.println("user:login:roles");
        return "验证权限成功";
    }
}
