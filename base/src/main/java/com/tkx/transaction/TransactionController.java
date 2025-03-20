package com.tkx.transaction;

import com.tkx.controller.UserController;
import com.tkx.mapper.StudentMapper;
import com.tkx.mapper.UserMapper;
import com.tkx.pojo.Student;
import com.tkx.pojo.StudentDTO;
import com.tkx.pojo.User;
import com.tkx.service.StudentInterface;
import com.tkx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Transactional的使用说明
 * 发现一个有趣的现象,事务回滚的时候，表里面的主键Id还是会消耗掉
 * 如果方法 在同一个类内部 被直接调用（this.method()），Spring 事务不会生效。
 **/
@RestController
@RequestMapping("/tx")
public class TransactionController {

    public static int v = 1;

    @Autowired
    private StudentInterface studentInterface;

    /**
     * 第一种情况,在单机中，数据库中的值进行了回滚
     * @param student
     * @param value
     * @return
     */
    @Transactional
    @PostMapping("/demo01")
    public String demo01(@RequestBody Student student, @RequestParam int value) {
        studentInterface.save(student);
        int v = 1/value;
        return "添加成功";
    }


    @Transactional
    @PostMapping("/demo02")
    public void demo02(){
        demo07();
        int i = 1/0;
    }

    /**
     * 不开启事务，开了等于没开
     */
    @Transactional(propagation = Propagation.NEVER)
    @PostMapping("/demo03")
    public void demo03(){
        Student student = new Student();
        student.setUsername(UUID.randomUUID().toString().substring(0,3));
        student.setAge(new Random().nextInt(20));
        studentInterface.save(student);
        int i = 1/0;
    }


    /**
     * 如果当前存在事务，则加入该事务。
     * 如果当前不存在事务，则创建一个新的事务。
     * 方法执行过程中如果发生异常，默认会回滚事务。
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("/demo04")
    public void demo04(){
        Student student = new Student();
        student.setUsername(UUID.randomUUID().toString().substring(0,3));
        student.setAge(new Random().nextInt(20));
        studentInterface.save(student);
        int i = 1/0;
    }

    /**
     * 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
     * 可以理解为这个事务注解加了等于没有，如果外部方法有注解，还是相当于有注解
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @PostMapping("/demo05")
    public void demo05(){
        Student student = new Student();
        student.setUsername(UUID.randomUUID().toString().substring(0,3));
        student.setAge(new Random().nextInt(20));
        studentInterface.save(student);
        int i = 1/0;
    }

    /**
     * 强执行的，必须要有事务，如果没有事务，直接抛出异常。
     * 有事务就以事务的方式执行
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @PostMapping("/demo06")
    public void demo06(){
        Student student = new Student();
        student.setUsername(UUID.randomUUID().toString().substring(0,3));
        student.setAge(new Random().nextInt(20));
        studentInterface.save(student);
    }
    @PostMapping("/demo")
    public void demo(){
       demo08();
    }
    @Transactional
    // @PostMapping("/demo08")
    public void demo08(){
        save();
        demo07();
        int i = 1/0;
    }
    /**
     * 挂起当前的事务，自己开一个新的事务.
     * 外面的注解不会影响到里面的注解
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // @PostMapping("/demo07") ###todo 加了这个注解会使上面的注解失效
    public void demo07(){
        Student student = new Student();
        student.setUsername(UUID.randomUUID().toString().substring(0,3));
        student.setAge(new Random().nextInt(20));
        studentInterface.save(student);
        System.out.println("demo07执行成功");
    }

    /**
     * 不支持事务,如果存在事务抛出异常
     */
    @Transactional(propagation = Propagation.NEVER)
    public void demo09(){

    }

    public void save(){
        Student student = new Student();
        student.setUsername(UUID.randomUUID().toString().substring(0,3));
        student.setAge(new Random().nextInt(20));
        studentInterface.save(student);
        System.out.println("save执行成功");
    }

}
