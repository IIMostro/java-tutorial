package org.ilmostro.vertx.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("websocket")
@Data
public class WebSocketProperties {

    private int port = 8081;
}
