package org.neptunus.rabbit.consumer;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.neptunus.rabbit.configuration.DelayMessageConfiguration;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class DelayCommandQueueProcess {

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = DelayMessageConfiguration.DLX_QUEUE_NAME, durable = "true"),
			exchange = @Exchange(value = DelayMessageConfiguration.DLX_EXCHANGE_NAME),
			key = DelayMessageConfiguration.DLX_ROUTING_KEY
	))
	public void process(Message message, Channel channel) throws Exception{
		log.info("message:[{}], ttl:[{}]", new String(message.getBody(), StandardCharsets.UTF_8), message.getMessageProperties().getExpiration());
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
	}
}
