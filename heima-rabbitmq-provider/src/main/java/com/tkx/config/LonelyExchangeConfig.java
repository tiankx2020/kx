package com.tkx.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tkx
 * @Date 2024 11 25 22 28
 **/
@Configuration
public class LonelyExchangeConfig {

    private String lonelyDirectExchangeName = CodeParam.CODE_LONELY_DIRECT_EXCHANGE;
    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange(lonelyDirectExchangeName);
    }
}
