package org.ilmostro.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic flinkTopic(){
        return new NewTopic("flink-stream-in-topic", 5, (short)1);
    }

}
