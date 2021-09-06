package org.ilmostro.start.redis;

import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
public class SecondRedisMessageHandlerAdapter extends RedisMessageHandlerAdapter{

    @Override
    protected String topic() {
        return "topic:second";
    }
}
