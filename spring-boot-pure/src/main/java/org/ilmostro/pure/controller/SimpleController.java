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

    public SimpleController(Vertx vertx) {
        this.vertx = vertx;
    }

    @GetMapping()
    public String publish(String message){
        vertx.eventBus().publish("ilmostro.handler", message);
        return message;
    }

    @GetMapping("/now")
    public String now(){
        log.info("current time: {}", System.nanoTime());
        return String.valueOf(System.nanoTime());
    }
}
