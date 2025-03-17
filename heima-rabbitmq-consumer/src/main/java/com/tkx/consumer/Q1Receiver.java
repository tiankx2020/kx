package com.tkx.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author tkx
 * @Date 2024 11 24 20 07
 **/
//监听的队列名称 ICS_MCS_Q_SMS_SEND
// 消费消息
@Component
@RabbitListener(queues = "q1")
public class Q1Receiver {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("q1消费者收到消息  : " + testMessage.toString());
    }


}
