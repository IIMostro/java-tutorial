package org.ilmostro.disruptor.service;

import com.lmax.disruptor.EventHandler;
import org.ilmostro.disruptor.entity.SheetElement;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author li.bowei
 */
@Service
public class ProcessService implements EventHandler<SheetElement> {

    private final StringRedisTemplate template;

    public ProcessService(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void onEvent(SheetElement event, long sequence, boolean endOfBatch) throws Exception {
        Integer integer = Optional.of(event)
                .map(SheetElement::getKey)
                .map(Objects::toString)
                .map(template.opsForValue()::get)
                .map(Integer::valueOf)
                .orElse(0);
        template.opsForValue().set(Objects.toString(event.getKey()), Objects.toString(integer + 1));
    }

    public boolean complete(Integer key, Integer size){
        Integer integer = Optional.ofNullable(key)
                .map(Objects::toString)
                .map(template.opsForValue()::get)
                .map(Objects::toString)
                .map(Integer::valueOf)
                .orElse(0);
        return size >= integer;
    }
}
