package org.ilmostro.user.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
public interface UserMessageProducer extends BasicProducer {

    String USER_INFO_MESSAGE = "user-info-message";

    /**
     * 用户信息的发送
     *
     * @return 标准返回
     */
    @Output(UserMessageProducer.USER_INFO_MESSAGE)
    MessageChannel info();
}
