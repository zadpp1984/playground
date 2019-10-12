package org.cay.play.springbootrabbitmqprovider.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TopicExchangeSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("ex1_xxx")
    public void ex1_xxx(@RequestParam String msg) {
        log.info("topic.exchange1.xxx send message: " + msg);
        rabbitTemplate.convertAndSend("Springboot.Exchange1", "topic.exchange1.xxx", msg);
    }

    @RequestMapping("ex1_xxxyyy")
    public void ex1_xxxyyy(@RequestParam String msg) {
        log.info("topic.exchange1.xxx.yyy send message: " + msg);
        rabbitTemplate.convertAndSend("Springboot.Exchange1", "topic.exchange1.xxx.yyy", msg);
    }


    @RequestMapping("ex2_xxx")
    public void ex2_xxx(@RequestParam String msg) {
        log.info("topic.exchange2.xxx send message: " + msg);
        rabbitTemplate.convertAndSend("Springboot.Exchange2", "topic.exchange2.xxx", msg);
    }

    @RequestMapping("ex2_xxxyyy")
    public void ex2_xxxyyy(@RequestParam String msg) {
        log.info("topic.exchange2.xxx.yyy send message: " + msg);
        rabbitTemplate.convertAndSend("Springboot.Exchange2", "topic.exchange2.xxx.yyy", msg);
    }

    @RequestMapping("ex2_xxxyyyzzz")
    public void ex2_xxxyyyzzz(@RequestParam String msg) {
        log.info("topic.exchange2.xxx.yyy.zzz send message: " + msg);
        rabbitTemplate.convertAndSend("Springboot.Exchange2", "topic.exchange2.xxx.yyy.zzz", msg);
    }
}