package org.ilmostro.user.configuration;

import org.ilmostro.user.stream.DepartmentMessageProducer;
import org.ilmostro.user.stream.LogProducer;
import org.ilmostro.user.stream.UserMessageProducer;
import org.ilmostro.user.stream.MessageReceive;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei on 2019-10-31.
 * @description
 */
@Configuration
@EnableBinding({UserMessageProducer.class,
        DepartmentMessageProducer.class,
        LogProducer.class,
        MessageReceive.class})
public class MessageConfiguration {
}
