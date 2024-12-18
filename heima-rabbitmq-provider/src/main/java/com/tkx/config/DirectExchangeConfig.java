package com.tkx.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author tkx
 * @Date 2024 11 24 18 51
 **/
@Configuration
public class DirectExchangeConfig {

    private String exchangeName = CodeParam.CODE_DIRECT_EXCHANGE;

    private String queueName = CodeParam.CODE_QUEUE_1;

    private String routingKey = CodeParam.CODE_DIRECT_ROUTING_KEY;


    // 创建一个queue
    @Bean
    public Queue TestDirectQueue1(){
        return new Queue(CodeParam.CODE_QUEUE_1,true);
    }


    @Bean
    public Queue TestDirectQueue2(){
        return new Queue(CodeParam.CODE_QUEUE_2,true);
    }
    // 创建一个交换机
    @Bean
    public DirectExchange TestDirectExchange(){
        return new DirectExchange(exchangeName,true,false);
    }

    // 绑定关系
    @Bean
    Binding bindingDirect1() {
        return BindingBuilder.bind(TestDirectQueue1()).to(TestDirectExchange()).with(routingKey);
    }

    @Bean
    Binding bindingDirect2(){
        return BindingBuilder.bind(TestDirectQueue2()).to(TestDirectExchange()).with(routingKey);
    }





}
