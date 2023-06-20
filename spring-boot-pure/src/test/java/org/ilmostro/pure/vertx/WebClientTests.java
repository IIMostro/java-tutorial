package org.ilmostro.pure.vertx;

import com.alibaba.fastjson.JSONArray;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.impl.WebClientBase;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.configuration.WebClientLoggingInterceptor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class WebClientTests {

    @Test
    void test_web_client_logging_should_work() throws Exception{
        final var vertx = Vertx.vertx();
        WebClientOptions options = new WebClientOptions();
        WebClientBase client = (WebClientBase)WebClient.create(vertx, options);
        client.addInterceptor(new WebClientLoggingInterceptor());
        CountDownLatch countDownLatch = new CountDownLatch(10);

        final var params = MultiMap.caseInsensitiveMultiMap();
        params.add("token", "6a61c1d1225e49f1ad0ee6f2d9d66efb");
        params.add("clientId", "edcbd053-18b");
        JSONArray array = new JSONArray();
        array.add("ipkg://15717987535@video.support.phone.cn1660096031984|xxxxS_tlbw00100830|smartFilter_phoneAlarm");
        array.add("ipkg://15717987535@video.support.phone.cn1660096031984|xxxxS_tlbw00100830|smartFilter");
        params.add("schemas", array.toJSONString());
        for (int i = 0; i < 10; i++) {
            client.postAbs("https://link-master.stg.closeli.cn/_magik/v1/schema/get")
                    .sendForm(params)
                    .onComplete(v1 -> countDownLatch.countDown());
        }
        countDownLatch.await();
    }
}
