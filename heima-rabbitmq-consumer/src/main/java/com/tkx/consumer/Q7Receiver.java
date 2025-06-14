package com.tkx.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author tkx
 * @Date 2025 04 09 21 39
 **/
@Component
@RabbitListener(queues = "q71")
// @ConditionalOnProperty(name = "rabbitmq.config.q7.enabled", havingValue = "true",matchIfMissing = false)
public class Q7Receiver {

    @Retryable(
            value = {Exception.class}, // 指定需要重试的异常类型
            maxAttempts = 2,                   // 最大重试次数
            backoff = @Backoff(delay = 2000)   // 每次重试的延迟时间（毫秒）
    )
    @RabbitHandler
    public void process(Map testMessage, Channel channel, Message messageObj) throws Exception{
        try {
            System.out.println("q5消费者收到消息  : " + testMessage.toString());
            int i = 1/0;
            channel.basicAck(messageObj.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            System.out.println("消息消费失败，准备重试");
            throw e;
        }
    }
    @Recover // 处理重试失败后的逻辑
    public void recover(Exception exception, Map testMessage, Channel channel, Message messageObj) {
        long deliveryTag = messageObj.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("fanout超过最大重试次数，拒绝消息");
            channel.basicReject(deliveryTag, false); // 拒绝消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
