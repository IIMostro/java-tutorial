package org.ilmostro.pure.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author li.bowei
 */
@ConfigurationProperties("netty.websocket")
@Data
public class NettyConfigurationProperties {

    private Integer port;
    private String ip;
    private Integer maxFrameSize;
}

