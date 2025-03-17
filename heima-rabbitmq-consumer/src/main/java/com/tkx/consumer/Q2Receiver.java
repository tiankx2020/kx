package com.tkx.consumer;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @Author tkx
 * @Date 2024 11 25 22 48
 **/
@Component
@RabbitListener(queues = "q2")
public class Q2Receiver {


    @Value("${value:}")
    private String value;
    @Retryable(
            value = {Exception.class}, // 指定需要重试的异常类型
            maxAttempts = 2,                   // 最大重试次数
            backoff = @Backoff(delay = 2000)   // 每次重试的延迟时间（毫秒）
    )
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message messageObj) throws Exception {
        System.out.println("value:"+value);
        try {
            System.out.println("q2消费者收到消息  : " + testMessage.toString());
            channel.basicAck(messageObj.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            System.out.println("消息消费失败");
            throw e; // 抛出异常以触发重试
        }
    }

    @Recover // 处理重试失败后的逻辑
    public void recover(Exception exception, Map testMessage, Channel channel, Message messageObj) {
        long deliveryTag = messageObj.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("超过最大重试次数，拒绝消息");
            channel.basicReject(deliveryTag, false); // 拒绝消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
