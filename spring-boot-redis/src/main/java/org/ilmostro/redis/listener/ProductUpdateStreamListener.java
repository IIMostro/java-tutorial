package org.ilmostro.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.configuration.RedissionConfiguration;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;

import java.util.Map;

/**
 * @author li.bowei
 */
@Slf4j
public class ProductUpdateStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

    private final RedisTemplate<String, Object> template;

    public ProductUpdateStreamListener(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        RecordId recordId = message.getId();
        String stream = message.getStream();
        Map<String, String> record = message.getValue();
        log.info("recordId:[{}], stream:[{}], record:[{}]",recordId, stream, record);
        template.opsForStream().acknowledge(RedissionConfiguration.REDIS_STREAM_GROUP, message);
    }
}
