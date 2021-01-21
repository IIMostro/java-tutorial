package org.ilmostro.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultService {

    private final KafkaTemplate template;

    public DefaultService(KafkaTemplate template) {
        this.template = template;
    }

    public void send() throws InterruptedException {
        for (; ; ) {
            template.send("flink-stream-in-topic", OrderEntity.getInstance());
            Thread.sleep(1000);
        }
    }

    @KafkaListener(groupId = "custom-consumer", topics = "flink-stream-in-topic")
    public void receive(String order) {
        log.info("receive:{}", order);
    }

    @KafkaListener(groupId = "custom-consumer", topics = "flink-stream-in-topic")
    public void receive_same(String order) {
        log.info("receive-same:{}", order);
    }

    @KafkaListener(id = "custom-consumer-1", topics = "flink-stream-in-topic")
    public void receive1(String order) {
        log.info("receive1:{}", order);
    }


    /**
     * 如果加上@link{Bean}注解则会新建
     */
    public void init() {
        TopicBuilder.name("topic-1").partitions(1).replicas(1);
    }
}
