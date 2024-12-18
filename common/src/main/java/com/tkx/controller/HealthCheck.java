package com.tkx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tkx
 * @Date 2024 12 08 22 49
 **/
@RestController
public class HealthCheck {


    /**
     * 健康检查
     * @return
     */
    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "ok";
    }
}
