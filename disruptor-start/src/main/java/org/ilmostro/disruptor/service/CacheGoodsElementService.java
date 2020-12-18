package org.ilmostro.disruptor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.disruptor.entity.GoodsElement;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheGoodsElementService implements EventHandler<GoodsElement> {

    private final StringRedisTemplate template;
    private static final String CACHE_KEY = "goods:%s";
    private final ObjectMapper objectMapper;

    public CacheGoodsElementService(StringRedisTemplate template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onEvent(GoodsElement event, long sequence, boolean endOfBatch) throws Exception {
        log.info("this sequence is :{}", sequence);
        String key = String.format(CACHE_KEY, event.getId());
        template.opsForValue().set(key, objectMapper.writeValueAsString(event));
//        log.info("this is cache service! and current thread is:{}", Thread.currentThread().getName());
    }
}
