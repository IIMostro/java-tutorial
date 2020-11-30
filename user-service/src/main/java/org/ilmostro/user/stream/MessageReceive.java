package org.ilmostro.user.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author li.bowei on 2019-10-30.
 * @description
 */
public interface MessageReceive {

    String USER_INFO_RECEIVE = "user-info-receive";

    String LOG_INFO_RECEIVE = "log-info-receive";

    String LOG_ERROR_RECEIVE = "log-error-receive";

    @Input(MessageReceive.USER_INFO_RECEIVE)
    SubscribableChannel info();

    @Input(LOG_INFO_RECEIVE)
    SubscribableChannel log();

    @Input(LOG_ERROR_RECEIVE)
    SubscribableChannel error();
}
