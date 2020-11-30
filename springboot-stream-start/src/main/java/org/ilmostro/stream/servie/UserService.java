package org.ilmostro.stream.servie;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.stream.client.UserClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/11/30 14:04
 */
@Service
@EnableBinding(UserClient.class)
@Slf4j
public class UserService {

    private final UserClient userClient;

    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public void send(String msg){
        userClient.info().send(MessageBuilder.withPayload(msg).build());
    }


    @StreamListener(UserClient.USER_INFO_MESSAGE)
    public void receive(Message<String> message){
        log.info("receive user client message:{}", message.getPayload());
    }
}
