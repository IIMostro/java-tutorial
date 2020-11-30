package org.ilmostro.user.stream;

import org.springframework.messaging.MessageChannel;

/**
 * @author li.bowei on 2019-10-31.
 * @description
 */
public interface BasicProducer {

    MessageChannel info();

    MessageChannel error();
}
