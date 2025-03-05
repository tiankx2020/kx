package com.tkx.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题交换机：使用routingKey去配置，匹配上了就转发，没有匹配上就不发 *匹配一些，#匹配一个或者多个
 */
@Configuration
public class TopicExchangeConfig {

    // 创建队列q3,q4
    @Bean
    public Queue TestDirectQueue5(){
        return new Queue(CodeParam.CODE_QUEUE_5,true);
    }

    @Bean
    public Queue TestDirectQueue6(){
        return new Queue(CodeParam.CODE_QUEUE_6,true);
    }

    @Bean
    public TopicExchange TestTopicExchange(){
        return new TopicExchange(CodeParam.CODE_TOPIC_EXCHANGE,true,false);
    }

    @Bean
    Binding bindingFanout5() {
        return BindingBuilder.bind(TestDirectQueue5()).to(TestTopicExchange()).with("tkx.happy");
    }

    @Bean
    Binding bindingFanout6() {
        return BindingBuilder.bind(TestDirectQueue6()).to(TestTopicExchange()).with("tkx.#");
    }
}
