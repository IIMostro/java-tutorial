package org.ilmostro.user.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author IlMostro
 * @date 2019-11-05 1:51
 */
public interface DepartmentMessageProducer extends BasicProducer {

    String DEPARTMENT_INFO_MESSAGE= "department-info-message";

    @Override
    @Output(DEPARTMENT_INFO_MESSAGE)
    MessageChannel info();
}
