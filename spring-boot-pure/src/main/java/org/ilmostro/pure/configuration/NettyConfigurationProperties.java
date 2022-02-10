package org.ilmostro.pure.configuration;

import lombok.Data;

/**
 * @author li.bowei
 */
//@ConfigurationProperties("netty.websocket")
@Data
public class NettyConfigurationProperties {

    private Integer port = 9091;
    private String ip = "0.0.0.0";
    private Integer maxFrameSize = 1024;
}

