package com.tkx.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tkx
 * @Date 2024 11 25 22 56
 **/
@Configuration
public class FanoutExchangeConfig {


    // 创建队列q3,q4
    @Bean
    public Queue TestDirectQueue3(){
        return new Queue(CodeParam.CODE_QUEUE_3,true);
    }

    @Bean
    public Queue TestDirectQueue4(){
        return new Queue(CodeParam.CODE_QUEUE_4,true);
    }
    @Bean
    public FanoutExchange TestFanoutExchange(){
        return new FanoutExchange(CodeParam.CODE_FANOUT_EXCHANGE,true,false);
    }


    // 绑定关系
    @Bean
    Binding bindingFanout1() {
        return BindingBuilder.bind(TestDirectQueue3()).to(TestFanoutExchange());
    }

    @Bean
    Binding bindingFanout2() {
        return BindingBuilder.bind(TestDirectQueue4()).to(TestFanoutExchange());
    }
}
