package org.ilmostro.vertx.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("vertx")
public class VertxProperties {

    private VertxClusterType type;
    private ZookeeperProperties zookeeper = new ZookeeperProperties();
    private WebSocketProperties websocket;


    public enum  VertxClusterType{
        hazelcast,
        zookeeper
    }

    @Getter
    @Setter
    public static class ZookeeperProperties{
        private String host;
        private String rootPath;
        private long sessionTimeout = 20000;
        private long connectTimeout = 3000;
        private ZookeeperRetryProperties retry = new ZookeeperRetryProperties();
    }

    //重试策略
    @Getter
    @Setter
    public static class ZookeeperRetryProperties{
        private long initialSleepTime;
        private long maxTimes;
        private long intervalTimes;
    }
}
