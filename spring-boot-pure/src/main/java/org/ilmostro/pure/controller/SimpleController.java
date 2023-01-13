package org.ilmostro.pure.controller;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

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

    public SimpleController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Vertx vertx,
            SimpleService service) {
        this.vertx = vertx;
        this.service = service;
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
//    @Counted(value = "custom_now_counted")
    public String now(){
        final Marker mark = MarkerFactory.getMarker("now-");
        log.info(mark, "current time: {}", System.nanoTime());
        return service.get("hello!");
    }

    @GetMapping("/throw")
//    @Counted(value = "custom_throw_counted", recordFailuresOnly = true)
    public String exception(){
        log.info("current time: {}", System.nanoTime());
        exc();
        return String.valueOf(System.nanoTime());
    }

    private void exc(){
        throw new RuntimeException("throw");
    }
}
