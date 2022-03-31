package org.ilmostro.pure.disruptor.order;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.ilmostro.pure.domain.GoodsElement;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Slf4j
public class IntegralWorkHandler implements WorkHandler<GoodsElement> {

    @Override
    public void onEvent(GoodsElement event) throws Exception {
        TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(100, 200));
        log.info("integral event handler -> good[{}] give integral", event);
    }
}
