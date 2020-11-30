package org.ilmostro.user.stream;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.user.enums.basic.MessageFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author li.bowei on 2019-10-31.
 * @description
 */
@Service
@Slf4j
public class MessageService {

    private final Executor executor;
    private final LogProducer logProducer;

    @Autowired
    public MessageService(Executor executor,
                          LogProducer logProducer) {
        this.executor = executor;
        this.logProducer = logProducer;
    }

    private static <T> Message<CustomMessage> build(T data, MessageFlag flag) {
        CustomMessage message = new CustomMessage<>(flag, data);
        return MessageBuilder.withPayload(message).build();
    }

    public <T> void send(T data, MessageFlag flag, BasicProducer producer) {

        Message<CustomMessage> message = build(data, flag);

        CompletableFuture.runAsync(() -> logProducer.info().send(message), executor)
                .exceptionally(ex -> {
                    log.error("send operator message error {}", ex);
                    return null;
                });

        CompletableFuture.supplyAsync(() -> message, executor)
                .thenAccept(var1 -> producer.info().send(var1))
                .exceptionally(ex -> {
                    log.error("send message error, {}", ex.getMessage());
                    logProducer.error().send(build(ex, MessageFlag.ERROR));
                    return null;
                });
    }

    public <T> void error(T data){
        Message<CustomMessage> message = build(data, MessageFlag.ERROR);
        CompletableFuture.runAsync(() -> logProducer.error().send(message), executor)
                .exceptionally(var1 -> {
                    log.error("send operator message error, {}", var1);
                    return null;
                });
    }
}
