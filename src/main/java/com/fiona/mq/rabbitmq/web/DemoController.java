package com.fiona.mq.rabbitmq.web;

import com.fiona.mq.rabbitmq.config.consts.RabbitConstants;
import com.fiona.mq.rabbitmq.provider.DemoSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 小尘哥
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private DemoSender demoSender;


    @RequestMapping("amqp")
    public String amqp() {
        rabbitTemplate.convertAndSend(RabbitConstants.QUEUE, "1message from web");
        rabbitTemplate.convertAndSend("exchange", "topic.messages", "2message from web for exchage");
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.ROUTINGKEY, "3message from web for fanoutExchange");

        //主要是下面这个
        demoSender.send("message from web for fanoutExchange1234234");
        return "success";
    }
}