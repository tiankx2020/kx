package com.tkx.zookeeper.controller;

import com.tkx.zookeeper.util.CuratorUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author tkx
 * @Date 2024 12 14 17 13
 **/
@RestController
@RequestMapping("/zookeeper")
public class ZookeeperController {


    @Resource
    private CuratorUtil  curatorUtil;

}
