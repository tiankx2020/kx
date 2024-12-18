package com.tkx.controller;


import com.tkx.config.CodeParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author tkx
 * @Date 2024 11 24 19 11
 **/
// 发送消息
@RestController
@RequestMapping("/tkx")
@Slf4j
public class SendMessageController {

    private String exchangeName = CodeParam.CODE_DIRECT_EXCHANGE;
    private String routingKey = CodeParam.CODE_DIRECT_ROUTING_KEY;


    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "test message, hello!";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> map = new HashMap<>();
            map.put("messageId", messageId);
            map.put("messageData", messageData);
            map.put("createTime", createTime);
            //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
            rabbitTemplate.convertAndSend(exchangeName, routingKey, map);
            log.info("messageId->{}->{},发送成功", i, messageId);
            Thread.sleep(100);
        }
        return "ok";
    }


    @GetMapping("/local/sendDirectExchange")
    public String sendDirectExchange() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "send direct message ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> womanMap = new HashMap<>();
            womanMap.put("messageId", messageId);
            womanMap.put("messageData", messageData);
            womanMap.put("createTime", createTime);
            rabbitTemplate.convertAndSend("direct_exchange", "key1", womanMap);
            log.info("sendTopicMessage2--->messageId->{}->{},发送成功", i, messageId);
            Thread.sleep(100);
        }
        return "ok";
    }

    @GetMapping("sendFanoutMessage")
    public String sendFanoutMessage() throws InterruptedException {
        String exchange = CodeParam.CODE_FANOUT_EXCHANGE;
        // 扇形exchange,routingKey 随便填
        String routingKey = "xxx";
        for (int i = 0; i < 100; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "send fanout message ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> womanMap = new HashMap<>();
            womanMap.put("messageId", messageId);
            womanMap.put("messageData", messageData);
            womanMap.put("createTime", createTime);
            rabbitTemplate.convertAndSend(exchange, routingKey, womanMap);
            log.info("sendFanoutMessage--->messageId->{}->{},发送成功", i, messageId);
            Thread.sleep(100);
        }
        return "ok";
    }

    @GetMapping("sendTopicMessage")
    public String sendTopicMessage() throws InterruptedException {
        String exchange = CodeParam.CODE_TOPIC_EXCHANGE;
        // 扇形exchange,routingKey 随便填
        String routingKey = "tkx.zzw";
        for (int i = 0; i < 100; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "send topic message ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> womanMap = new HashMap<>();
            womanMap.put("messageId", messageId);
            womanMap.put("messageData", messageData);
            womanMap.put("createTime", createTime);
            rabbitTemplate.convertAndSend(exchange, routingKey, womanMap);
            log.info("sendTopicMessage--->messageId->{}->{},发送成功", i, messageId);
            Thread.sleep(100);
        }
        return "ok";
    }

    @GetMapping("sendAllTopicMessage")
    public String sendAllTopicMessage() throws InterruptedException {
        String exchange = CodeParam.CODE_TOPIC_EXCHANGE;
        // 扇形exchange,routingKey 随便填
        String routingKey = "tkx.happy";
        for (int i = 0; i < 100; i++) {
            String messageId = String.valueOf(UUID.randomUUID());
            String messageData = "send topic message ";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> womanMap = new HashMap<>();
            womanMap.put("messageId", messageId);
            womanMap.put("messageData", messageData);
            womanMap.put("createTime", createTime);
            rabbitTemplate.convertAndSend(exchange, routingKey, womanMap);
            log.info("sendAllTopicMessage--->messageId->{}->{},发送成功", i, messageId);
            Thread.sleep(100);
        }
        return "ok";
    }

    // 推送一个没有匹配exchange的消息
    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }

    // 推送一个有交换机，但是没有queue的message
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

}

