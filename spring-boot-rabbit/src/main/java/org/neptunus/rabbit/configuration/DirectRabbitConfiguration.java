package org.neptunus.rabbit.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei
 */
@Configuration
public class DirectRabbitConfiguration {

    @Bean
    public Queue simpleDirectQueue(){
        return new Queue("simple:queue", true);
    }

    @Bean
    public DirectExchange simpleDirectExchange(){
        return new DirectExchange("simple", true, false);
    }

    @Bean
    public Binding binding(Queue simpleDirectQueue, DirectExchange simpleDirectExchange){
        return BindingBuilder.bind(simpleDirectQueue).to(simpleDirectExchange).with("#");
    }
}
