package org.ilmostro.redis.listener;

import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author li.bowei
 */
public class MessageListenerAdapterWrapper extends MessageListenerAdapter{

    private String topic;

    public MessageListenerAdapterWrapper(Object delegate) {
        super(delegate);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
