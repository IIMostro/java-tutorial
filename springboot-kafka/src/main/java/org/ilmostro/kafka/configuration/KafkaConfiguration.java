package org.ilmostro.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@Configuration
public class KafkaConfiguration {

    private final KafkaProperties properties;

    public KafkaConfiguration(KafkaProperties properties) {
        this.properties = properties;
    }

//    @Bean
    public NewTopic flinkTopic(){
        return TopicBuilder.name("flink-stream-in-topic").partitions(1).replicas(1).build();
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> kafkaProducerFactory,
                                                       ProducerListener<String, String> kafkaProducerListener,
                                                       ObjectProvider<RecordMessageConverter> messageConverter) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        messageConverter.ifUnique(kafkaTemplate::setMessageConverter);
        kafkaTemplate.setProducerListener(kafkaProducerListener);
        return kafkaTemplate;
    }


    @Bean
    public ProducerFactory<String, String> kafkaProducerFactory(
            ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(
                this.properties.buildProducerProperties());
        String transactionIdPrefix = this.properties.getProducer().getTransactionIdPrefix();
        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
        return factory;
    }

    @Bean
    public ProducerListener<String, String> kafkaProducerListener() {
        return new LoggingProducerListener<>();
    }

}
