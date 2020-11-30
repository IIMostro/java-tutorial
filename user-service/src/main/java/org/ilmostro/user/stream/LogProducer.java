package org.ilmostro.user.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author li.bowei on 2019-10-31.
 * @description
 */
public interface LogProducer extends BasicProducer {

    String LOG_INFO = "log-info";

    String LOG_ERROR = "log-error";

    @Output(LOG_INFO)
    MessageChannel info();

    @Output(LOG_ERROR)
    MessageChannel error();
}
