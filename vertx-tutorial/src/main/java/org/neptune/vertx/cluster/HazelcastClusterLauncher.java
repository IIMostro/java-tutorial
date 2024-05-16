package org.neptune.vertx.cluster;

import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HazelcastClusterLauncher extends Launcher {

    @Override
    public void beforeStartingVertx(VertxOptions options) {
        final var manager = new HazelcastClusterManager();
        log.info("HazelcastClusterLauncher beforeStartingVertx");
        options.setClusterManager(manager);
    }
}
