package org.cay.play.springbootrabbitmqprovider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
public class QueueConfig {
    @Bean
    public Queue queue1() {
        return new Queue("Springboot.Queue1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("Springboot.Queue2");
    }

    @Bean
    public TopicExchange exchange1() {
        return new TopicExchange("Springboot.Exchange1");
    }

    @Bean
    public TopicExchange exchange2() {
        return new TopicExchange("Springboot.Exchange2");
    }

    @Bean
    public Binding bindingTopicExchange1(Queue queue1, TopicExchange exchange1) {
        return BindingBuilder.bind(queue1).to(exchange1).with("topic.exchange1.*");
    }

    @Bean
    public Binding bindingTopicExchange2(Queue queue2, TopicExchange exchange2) {
        return BindingBuilder.bind(queue2).to(exchange2).with("topic.exchange2.#");
    }
}
