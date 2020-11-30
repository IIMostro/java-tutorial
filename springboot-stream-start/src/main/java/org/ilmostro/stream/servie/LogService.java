package org.ilmostro.stream.servie;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.stream.client.LogClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/11/30 14:15
 */
@Service
@EnableBinding(LogClient.class)
@Slf4j
public class LogService {

    private final LogClient logClient;

    public LogService(LogClient logClient) {
        this.logClient = logClient;
    }

    public void send(String msg){
        logClient.logInfoOut().send(MessageBuilder.withPayload(msg).build());
    }

    @StreamListener(LogClient.LOG_INFO)
    public void receive(Message<String> message){
        log.info("receive receive message:{}", message.getPayload());
    }
}
