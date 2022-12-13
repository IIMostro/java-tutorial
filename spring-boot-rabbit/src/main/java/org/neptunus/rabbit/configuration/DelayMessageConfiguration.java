package org.neptunus.rabbit.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei
 */
@Configuration
public class DelayMessageConfiguration {

	public static final String DLX_EXCHANGE_NAME = "command_dlx_exchange";
	public static final String DLX_QUEUE_NAME = "command_dlx_queue";
	public static final String DLX_ROUTING_KEY = "command.dlx.routing.key";

	public static final String COMMAND_EXCHANGE_NAME = "command_exchange";
	public static final String COMMAND_QUEUE_NAME = "command_queue";
	public static final String COMMAND_ROUTING_KEY = "#";

	@Bean
	public DirectExchange dlxExchange(){
		return new DirectExchange(DLX_EXCHANGE_NAME, true, false);
	}

	@Bean
	public Queue dlxQueue(){
		return new Queue(DLX_QUEUE_NAME);
	}

	@Bean
	public Binding dlxBinding(){
		return BindingBuilder.bind(dlxQueue())
				.to(dlxExchange())
				.with(DLX_ROUTING_KEY);
	}


	@Bean
	public Queue commandQueue(){
		// 绑定死信队列
		return QueueBuilder.durable(COMMAND_QUEUE_NAME)
				.deadLetterExchange(DLX_EXCHANGE_NAME)
				.deadLetterRoutingKey(DLX_ROUTING_KEY)
				.build();
	}

	@Bean
	public TopicExchange commandExchange(){
		return ExchangeBuilder.topicExchange(COMMAND_EXCHANGE_NAME)
				.durable(true)
				.build();
	}

	@Bean
	public Binding commandBinding(){
		return BindingBuilder.bind(commandQueue())
				.to(commandExchange())
				.with(COMMAND_ROUTING_KEY);
	}
}
