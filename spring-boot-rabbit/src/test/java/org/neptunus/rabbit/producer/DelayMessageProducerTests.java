package org.neptunus.rabbit.producer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.neptunus.rabbit.configuration.DelayMessageConfiguration;
import org.neptunus.rabbit.domain.User;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

/**
 * @author li.bowei
 */
@SpringBootTest
public class DelayMessageProducerTests {

	@Autowired
	private RabbitTemplate template;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Test
	void send(){
		for (int i = 0; i < 10; i++) {
			final User user = new User(i, formatter.format(LocalDateTime.now()));
			final Message message = MessageBuilder.withBody(JSON.toJSONString(user).getBytes())
					.setExpiration(String.valueOf(i * 1000))
					.setContentType(MediaType.APPLICATION_JSON_VALUE)
					.build();
			template.convertAndSend(DelayMessageConfiguration.COMMAND_EXCHANGE_NAME,
					DelayMessageConfiguration.COMMAND_ROUTING_KEY,
					message);
		}
	}
}
