package com.tkx.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author tkx
 * @Date 2024 11 24 20 07
 **/
// 消费消息
@Component
@RabbitListener(queues = "q6")
public class Q6Receiver {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("q6消费者收到消息  : " + testMessage.toString());
    }


}
