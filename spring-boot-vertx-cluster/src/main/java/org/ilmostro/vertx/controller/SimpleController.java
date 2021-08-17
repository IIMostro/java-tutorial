package org.ilmostro.vertx.controller;

import io.vertx.core.Vertx;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
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
}
