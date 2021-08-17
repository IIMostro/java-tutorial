package org.ilmostro.vertx.configuration;

import com.hazelcast.core.HazelcastInstance;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.attribute.standard.CopiesSupported;

@Configuration
public class VertxConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(VertxConfiguration.class);

    @Bean
    public Vertx vertx(HazelcastInstance hazelcastInstance) {
        HazelcastClusterManager clusterManager = new HazelcastClusterManager(hazelcastInstance);
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager);
        Future<Vertx> future = Vertx.clusteredVertx(options);
        if (hazelcastInstance.getLifecycleService().isRunning()) {
            logger.info("hazelcast is running");
        } else {
            logger.info("hazelcast status:{}", hazelcastInstance.getLifecycleService().isRunning());
        }
        //noinspection StatementWithEmptyBody
        while (!future.isComplete()) {

        }
        return future.result();
    }
}
