package org.ilmostro.pure.controller;

import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.pure.service.SimpleService;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
@Slf4j
public class SimpleController {

    private final Vertx vertx;
    private final SimpleService service;

    private final MeterRegistry registry;

    public SimpleController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Vertx vertx,
            SimpleService service, MeterRegistry registry) {
        this.vertx = vertx;
        this.service = service;
        this.registry = registry;
    }

    @GetMapping()
    public String publish(String message){
        vertx.eventBus().publish("ilmostro.handler", message);
        return message;
    }

    @PostMapping("/stream")
    public String stream(HttpServletRequest request) throws Exception{
        // tomcat 返回的是CoyoteInputStream
        final ServletInputStream inputStream = request.getInputStream();
        return "hello";
    }

    @GetMapping("/now")
    @Counted(value = "normal", extraTags = {"path", "normal"})
    public String now(){
        final Marker mark = MarkerFactory.getMarker("now-");
        log.info(mark, "current time: {}", System.nanoTime());
        return service.get("hello!");
    }

    @GetMapping("/metrics")
    public String metrics(){
        final List<Meter> meters = registry.getMeters();
        for (Meter meter : meters) {
            for (Measurement measurement : meter.measure()) {
                log.info("measurement name:[{}] :[{}], value:[{}]",meter.getId().getName(), measurement.getStatistic().getTagValueRepresentation(), measurement.getValue());
            }
        }
        return null;
    }

    @GetMapping("/throw")
    @Counted(value = "throw.error.controller", recordFailuresOnly = true, extraTags = {"path", "throw"})
    public String exception(Integer number){
        log.info("current time: {}", System.nanoTime());
        if (number % 2 == 0) exc();
        return String.valueOf(System.nanoTime());
    }

    private void exc(){
        throw new RuntimeException("throw");
    }
}
