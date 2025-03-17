package com.tkx.base.controller;

import com.tkx.controller.UserController;
import com.tkx.mapper.StudentMapper;
import com.tkx.mapper.UserMapper;
import com.tkx.pojo.Student;
import com.tkx.pojo.StudentDTO;
import com.tkx.pojo.User;
import com.tkx.service.StudentInterface;
import com.tkx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author tkx
 * @Date 2025 03 17 20 59
 **/
@RestController
@RequestMapping("/tx")
public class TransactionController {

    public static  int  v = 1;

    @Autowired
    private StudentInterface studentInterface;
    @Transactional
    @PostMapping("/demo01")
    public void demo01(){
        System.out.println("======="+v);
        v++;
        List<Student> list = studentInterface.getList(new StudentDTO());
        System.out.println(list);
    }
}
