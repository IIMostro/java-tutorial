package org.neptune.vertx.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HazelcastClusterVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(HazelcastClusterVerticle.class);

    @Override
    public void start(Promise<Void> promise) throws Exception {
        log.info("HazelcastCluster start {}", vertx.isClustered());
    }
}
