package org.ilmostro.pure.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.shell.ShellService;
import io.vertx.ext.shell.ShellServiceOptions;
import io.vertx.ext.shell.term.TelnetTermOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShellServiceVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) throws Exception {
        final var options = new ShellServiceOptions();
        final var telnet = new TelnetTermOptions();
        telnet.setHost("localhost");
        telnet.setPort(4000);
        options.setTelnetOptions(telnet);
        final var service = ShellService.create(vertx, options);
        service.start();
        log.info("shell service start success");
        promise.complete();
    }
}
