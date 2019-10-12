package org.cay.play.springbootrabbitmqprovider.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicExchangeReceive {
    @RabbitHandler
    @RabbitListener(queues = "Springboot.Queue1")
    public void user(String msg) {
        log.info("Springboot.Queue1 receive message: " + msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "Springboot.Queue2")
    public void order(String msg) {
        log.info("Springboot.Queue2 receive message: " + msg);
    }
}
