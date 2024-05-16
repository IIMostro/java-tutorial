package org.neptune.vertx.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HazelcastClusterVerticle extends AbstractVerticle {


    @Override
    public void start(Promise<Void> promise) throws Exception {
        log.info("HazelcastCluster start {}", vertx.isClustered());
    }
}
