package com.tkx.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author tkx
 * @Date 2024 11 25 22 48
 **/
@Component
@RabbitListener(queues = "q2")
public class Q2Receiver {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("q2消费者收到消息  : " + testMessage.toString());
    }


}
