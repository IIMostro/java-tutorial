package org.ilmostro.pure.disruptor.order;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventTranslatorOneArg;
import org.ilmostro.pure.domain.GoodsElement;

/**
 * @author li.bowei
 */
public class GoodElementTranslator implements EventTranslatorOneArg<GoodsElement, String> {

    @Override
    public void translateTo(GoodsElement event, long sequence, String arg0) {
        GoodsElement good = JSON.parseObject(arg0, GoodsElement.class);
        event.copy(good);
    }
}
