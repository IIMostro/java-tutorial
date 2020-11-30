package org.ilmostro.mqtt.configuration;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.function.Consumer;

/**
 * @author li.bowei
 * @date 2020/11/16 19:24
 */
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttServerConfiguration {

    private final MqttProperties properties;

    public MqttServerConfiguration(MqttProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MqttPahoClientFactory getFactory() {
        DefaultMqttPahoClientFactory defaultMqttPahoClientFactory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = properties.getOptions();
        defaultMqttPahoClientFactory.setConnectionOptions(options);
        return defaultMqttPahoClientFactory;
    }

    @Bean
    public MessageProducer defaultProducer(MqttPahoClientFactory factory,
                                           Environment environment,
                                           MessageChannel mqttMessageChannel) {
        String name = environment.getProperty("spring.application.name");
        MqttProperties.MqttChannelProperties inner = properties.getInner();
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(name, factory, inner.getTopics());
        adapter.setOutputChannel(mqttMessageChannel);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setCompletionTimeout(inner.getCompletionTimeout());
        adapter.setQos(inner.getQos());
        return adapter;
    }

    @Bean
    public MessageChannel mqttMessageChannel() {
        return new DirectChannel();
    }

    @Bean
    public Consumer<Message<?>> consumer() {
        return message -> System.out.println(message.getPayload());
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttMessageChannel")
    public MessageHandler handler(Consumer<Message<?>> consumer) {
        return consumer::accept;
    }
}
