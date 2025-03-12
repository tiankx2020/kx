// package com.tkx.config;
//
// import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
// import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
// import org.springframework.amqp.rabbit.retry.MessageRecoverer;
// import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.retry.backoff.FixedBackOffPolicy;
// import org.springframework.retry.policy.SimpleRetryPolicy;
// import org.springframework.retry.support.RetryTemplate;
//
// /**
//  * @Author tkx
//  * @Date 2025 03 12 20 04
//  **/
// @Configuration
// public class RabbitMQConfig {
//
//     @Autowired
//     private ConnectionFactory connectionFactory;
//
//     @Bean
//     public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory() {
//         SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//         factory.setConnectionFactory(connectionFactory);
//
//         // 配置 RetryTemplate
//         RetryTemplate retryTemplate = new RetryTemplate();
//         SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
//         retryPolicy.setMaxAttempts(3); // 设置最大重试次数为 3 次
//
//         FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
//         backOffPolicy.setBackOffPeriod(1000); // 设置每次重试间隔为 1 秒
//
//         retryTemplate.setRetryPolicy(retryPolicy);
//         retryTemplate.setBackOffPolicy(backOffPolicy);
//
//         factory.setAdviceChain(StatelessRetryOperationsInterceptor.builder(retryTemplate).build());
//
//         // 配置消息恢复策略（可选）
//         MessageRecoverer recoverer = new RepublishMessageRecoverer(rabbitTemplate, "errorExchange", "errorRoutingKey");
//         factory.setErrorHandler(new ConditionalRejectingErrorHandler(recoverer));
//
//         return factory;
//     }
// }
