package com.tkx.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tkx
 * @Date 2025 05 17 16 34
 **/
@RestController
@RequestMapping("/exception")
public class ExceptionController {


    @PostMapping("/e1")
    public void exception1(){
        if(1==1){
            throw new RuntimeException("<UNK>");
        }
    }
}
