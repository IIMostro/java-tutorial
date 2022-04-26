package org.ilmostro.pure.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
@Slf4j
public class SimpleController {

    private final Vertx vertx;
    private final Counter counter;

    public SimpleController(Vertx vertx,
            MeterRegistry registry) {
        this.vertx = vertx;
        counter = registry.counter("simple_controller_http_request_counter");
    }

    @GetMapping()
    public String publish(String message){
        vertx.eventBus().publish("ilmostro.handler", message);
        return message;
    }

    @GetMapping("/now")
    public String now(){
        counter.increment();
        log.info("current time: {}", System.nanoTime());
        return String.valueOf(System.nanoTime());
    }
}
