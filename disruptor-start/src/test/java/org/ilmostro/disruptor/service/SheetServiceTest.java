package org.ilmostro.disruptor.service;

import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.disruptor.entity.GoodsElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SheetServiceTest {

    @Autowired
    @Qualifier("factory")
    private Disruptor<GoodsElement> disruptor;

    private List<String> names;

    @Before
    public void before(){
        names = Stream.generate(UUID::randomUUID).limit(1000).map(UUID::toString).collect(Collectors.toList());
    }

    @Test
    public void handler() {
        StopWatch watch = new StopWatch();
        watch.start();
        for (int index = 0; index < 1000; index++) {
            int finalIndex = index;
            String format = names.get(index);
            disruptor.publishEvent((v1, v2) -> {
                v1.setId(finalIndex);
                v1.setName(format);
                v1.setDescription(format);
            });
        }
        watch.stop();
        log.info("end :{}", watch.getTotalTimeSeconds());
    }
}