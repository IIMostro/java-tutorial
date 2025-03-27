package org.ilmostro.pure.vertx;

import cn.hutool.core.util.HexUtil;
import com.alibaba.fastjson.JSONArray;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.impl.WebClientBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.sshd.common.digest.Digest;
import org.ilmostro.pure.configuration.WebClientLoggingInterceptor;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

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


    @Test
    void test_havok_signature_should_work() throws Exception {
        final var vertx = Vertx.vertx();
        WebClientOptions options = new WebClientOptions();
        WebClientBase client = (WebClientBase)WebClient.create(vertx, options);
        client.addInterceptor(new WebClientLoggingInterceptor());
        final var timestamp = System.currentTimeMillis();

        final var md5 = MessageDigest.getInstance("MD5");
        final var md5_context = md5.digest("a=a&b=b".getBytes(StandardCharsets.UTF_8));
        String message="z558i0r3jswpw8o8ojfuyrvi1b78ihov"+","+"8x1uy8cuimdu2lj0aeer8h8a"+","+timestamp+","+"/api/test"+","+md5_context;
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec("z558i0r3jswpw8o8ojfuyrvi1b78ihov".getBytes("UTF-8"), "HmacSHA256");
        hmacSha256.init(secretKeySpec);
        byte[] bytes = hmacSha256.doFinal(message.getBytes("UTF-8"));
        String signature = Base64.getEncoder().encodeToString(bytes);
        final var result = client.getAbs("http://192.168.27.117:1228/api/test")
            .addQueryParam("a", "a")
            .addQueryParam("b", "b")
            .putHeader("timestamp", Objects.toString(timestamp))
            .putHeader("appid", "8x1uy8cuimdu2lj0aeer8h8a")
            .putHeader("signature", signature)
            .send()
                .result();
        System.out.println(result);
    }

    @Test
    void test_md5_should_work() throws Exception {
        final var md5 = MessageDigest.getInstance("MD5");
        final var md5_context = md5.digest("{}".getBytes(StandardCharsets.UTF_8));
        System.out.println(Hex.encodeHexString(md5_context));
    }

    @Test
    void test_signature_should_work() throws Exception {
        final var timestamp = System.currentTimeMillis();
        String message="z558i0r3jswpw8o8ojfuyrvi1b78ihov"+","+"8x1uy8cuimdu2lj0aeer8h8a"+","+timestamp+","+"/api/test"+","+"bfd560d24660c80704d4f0abcf2e6f13";
        System.out.println(message);
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec("z558i0r3jswpw8o8ojfuyrvi1b78ihov".getBytes("UTF-8"), "HmacSHA256");
        hmacSha256.init(secretKeySpec);
        byte[] bytes = hmacSha256.doFinal(message.getBytes("UTF-8"));
        System.out.println(org.apache.commons.codec.binary.Base64.encodeBase64String(bytes));
    }

    @Test
    void test_request_should_work() throws Exception {
        final var context = """
            {"account": "RgfNg3pqSHjgLeCTf3W+qX1zcrhUb/ukbzoiJxGJXfrVB03N9fH7TkXwO/yf35rdBO06PCM8/f1OxEqj62ePRkFosCnBX0o0l0JkR9vJZA/vNdZjEq/KQgJkVZJ0491SOD8uDw2hGkN1YsyMempeNUXnM5QY46vu0Oqy+gipL50=","password":"t0HKn11SmaM+1MTjvg5h8rA8NjADnDZZ1Fsog635W9xAKGqGSLEK2d6Jdw4UshoV9nvGQtYVwheBEpF9y88cV1GOpiPr98KxME0lrKK4dEN0O4RYviNXOfKNSpjqdzgiaRTfFClinG1hpUcbksfqBxYVeOWYNUnK4j1BwMgZ3xc=","platform":"WEB"}
            """;
        final var md5 = MessageDigest.getInstance("MD5");
        final var md5_context = md5.digest(context.getBytes(StandardCharsets.UTF_8));
        final var digest = Hex.encodeHexString(md5_context);
        final var timestamp = System.currentTimeMillis();
        // dl1s88c9ggb3tg7058x0bo1g	z37vc7dslsm8mqjiojslncf9v8142x7g
        String message="z37vc7dslsm8mqjiojslncf9v8142x7g"+","+"dl1s88c9ggb3tg7058x0bo1g"+","+timestamp+","+"/api/test"+","+digest;
        System.out.println(message);
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec("z37vc7dslsm8mqjiojslncf9v8142x7g".getBytes("UTF-8"), "HmacSHA256");
        hmacSha256.init(secretKeySpec);
        byte[] bytes = hmacSha256.doFinal(message.getBytes("UTF-8"));
        final var signature = org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
        final var latch = new CountDownLatch(1);
        final var result = WebClient.create(Vertx.vertx())
            .post("http://111.10.38.158:30397/api/v1/user/login")
            .putHeader("timestamp", Objects.toString(timestamp))
            .putHeader("signature", signature)
            .putHeader("appid", "dl1s88c9ggb3tg7058x0bo1g")
            .sendJson(Json.decodeValue(context))
                .onComplete(v1 -> latch.countDown());
        latch.await();
        System.out.println(result.result().bodyAsString());
    }
}
