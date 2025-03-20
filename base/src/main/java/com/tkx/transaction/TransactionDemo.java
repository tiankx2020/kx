package com.tkx.transaction;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author tkx
 * @Date 2025 03 20 21 27
 **/
@RequestMapping("/txdemo")
@RestController
public class TransactionDemo {

    @Resource
    private TransactionController transaction;


    /***
     * 不能使用内部方法，内部事务失效
     */
    @PostMapping("/demo01")
    @Transactional
    public void demo01(){
        TransactionDemo d1 = new TransactionDemo();
        TransactionDemo d2 = new TransactionDemo();
        d2.demo02();
        this.demo02();
        transaction.demo09();
    }

    /**
     * 存在事务，并且里面的方法使用了 Propagation.NEVER传播行为
     * 但是里面的事务已经失效了，因为使用的同一个对象的内部方法
     */
    @Transactional(propagation = Propagation.NEVER)
    public void demo02(){
        // transaction.demo09();
    }

    @Transactional
    @PostMapping("/demo03")
    public void demo03(){
        {  // 不会抛出异常 ,因为transactionController的demo09方法没有被spring代理
            TransactionController transactionController = new TransactionController();
            transactionController.demo09();
        }
        {   // 会抛出异常 因为transaction被代理了
            transaction.demo09();
        }
    }
}
