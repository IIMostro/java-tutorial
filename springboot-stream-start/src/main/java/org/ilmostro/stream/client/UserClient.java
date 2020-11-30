package org.ilmostro.stream.client;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author li.bowei
 * @date 2020/11/30 10:21
 */
public interface UserClient {

    String USER_INFO_MESSAGE = "user-info-message";

    @Output(USER_INFO_MESSAGE)
    MessageChannel info();
}
