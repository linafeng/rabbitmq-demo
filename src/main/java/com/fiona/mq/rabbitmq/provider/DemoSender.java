package com.fiona.mq.rabbitmq.provider;

import com.fiona.mq.rabbitmq.config.consts.RabbitConstants;
import com.fiona.mq.rabbitmq.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小尘哥
 */
@Component
public class DemoSender implements RabbitTemplate.ConfirmCallback{

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoSender.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public DemoSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    public void send(String msg) {
        CorrelationData correlationData = new CorrelationData(RedisUtil.getIncr(RedisUtil.GENID_KEY).toString());
        LOGGER.info("send: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.ROUTINGKEY, msg, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        System.out.println("confirm: " + correlationData.getId());

    }
}
