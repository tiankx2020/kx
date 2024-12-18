package com.tkx.controller;

import com.tkx.service.RedisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author tkx
 * @Date 2024 12 15 21 13
 **/
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;
    @PostMapping("/get")
    public Object get(@RequestParam("key")String key){
        return redisService.getKey(key);
    }

    @PostMapping("/set")
    public void set(@RequestParam("key") String key,@RequestParam("value") Object value){
        redisService.setKey(key,value);
    }


    @PostMapping("/setList")
    public void setList(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        redisService.setList("listKey",list);
    }

    @PostMapping("/getList")
    public List<Object> getList(){
        String key = "listKey";
        return redisService.getList(key);
    }



}
