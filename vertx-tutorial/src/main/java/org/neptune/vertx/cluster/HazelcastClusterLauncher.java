package org.neptune.vertx.cluster;

import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HazelcastClusterLauncher extends Launcher {

    private static final Logger log = LoggerFactory.getLogger(HazelcastClusterVerticle.class);

    @Override
    public void beforeStartingVertx(VertxOptions options) {
        final var manager = new HazelcastClusterManager();
        log.info("HazelcastClusterLauncher beforeStartingVertx");
        options.setClusterManager(manager);
    }
}
