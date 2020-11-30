package org.ilmostro.stream.client;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author li.bowei
 * @date 2020/11/30 10:24
 */
public interface LogClient {

    String LOG_INFO = "log-info";
    String LOG_ERROR = "log-error";


    @Output(LOG_INFO)
    MessageChannel logInfoOut();

    @Output(LOG_ERROR)
    MessageChannel logErrorOut();
}
