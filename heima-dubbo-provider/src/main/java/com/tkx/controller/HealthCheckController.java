package com.tkx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tkx
 * @Date 2024 12 07 21 01
 **/
@RestController
public class HealthCheckController {


    @GetMapping("/health/check")
    public String healthCheck(){
        return "ok";
    }
}
