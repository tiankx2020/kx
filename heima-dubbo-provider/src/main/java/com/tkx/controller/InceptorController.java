package com.tkx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tkx
 * @Date 2025 04 15 23 39
 **/
@RestController
public class InceptorController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/login")
    public String login() {
        return "Login Page";
    }
}
