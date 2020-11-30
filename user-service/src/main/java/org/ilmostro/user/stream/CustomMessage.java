package org.ilmostro.user.stream;

import lombok.Data;
import org.ilmostro.user.enums.basic.MessageFlag;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
@Data
public class CustomMessage<T> {

    private MessageFlag flag;
    private T data;

    public CustomMessage(MessageFlag flag, T data) {
        this.flag = flag;
        this.data = data;
    }

    public CustomMessage() {
    }
}
