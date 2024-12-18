package com.tkx.controller;

import com.tkx.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tkx
 * @Date 2024 12 07 21 26
 **/
@RestController
public class DubboController {

    @Reference
    private UserService userService;


    @Reference(version = "v1.0")
    private UserService userServiceV1;

    @Reference(version = "v2.0")
    private UserService userServiceV2;

    @GetMapping("/dubbo")
    public String dubbo(){
        return userService.sayHello();
    }

    @GetMapping("query")
    public String query(){
        return userService.query(1).toString();
    }

    @GetMapping("queryV1")
    public String queryV1(){
        return userServiceV1.query(1).toString();
    }

    @GetMapping("queryV2")
    public String queryV2(){
        return userServiceV2.query(1).toString();
    }


}
