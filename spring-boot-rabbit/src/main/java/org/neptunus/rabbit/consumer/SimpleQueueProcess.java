package org.neptunus.rabbit.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.neptunus.rabbit.domain.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class SimpleQueueProcess {

    public final ObjectMapper objectMapper;

    public SimpleQueueProcess(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "simple:queue", durable = "true"),
            exchange = @Exchange(value = "simple", durable = "true", type = "direct"),
            key = "#"
    ))
    public void process(Message message, Channel channel) throws IOException {
        log.info("receive:{}", objectMapper.readValue(message.getBody(), User.class));
        log.info("receive message properties:{}", message.getMessageProperties());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
