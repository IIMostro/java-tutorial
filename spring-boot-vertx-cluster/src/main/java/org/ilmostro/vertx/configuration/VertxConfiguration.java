package org.ilmostro.vertx.configuration;

import com.hazelcast.core.HazelcastInstance;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableConfigurationProperties(WebSocketProperties.class)
public class VertxConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(VertxConfiguration.class);
    private static final AtomicBoolean circle = new AtomicBoolean(false);

    @Bean
    public Vertx vertx(HazelcastInstance hazelcastInstance,
                       VertxSpringFactory factory) {
        HazelcastClusterManager clusterManager = new HazelcastClusterManager(hazelcastInstance);
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager);
        Future<Vertx> future = Vertx.clusteredVertx(options);
        if (hazelcastInstance.getLifecycleService().isRunning()) {
            logger.info("hazelcast is running");
        } else {
            logger.info("hazelcast status:{}", hazelcastInstance.getLifecycleService().isRunning());
        }
        long clusterStartTime = System.currentTimeMillis();
        logger.info("vertx cluster initing....");
        logger.info("vertx claster init time :{}", clusterStartTime);
        //自旋, 等待future完成
        while (circle.compareAndSet(false, future.isComplete())) {

        }
        logger.info("vertx cluster inited....");
        logger.info("vertx claster init finshed time :{}", System.currentTimeMillis());
        Vertx result = future.result();
        result.registerVerticleFactory(factory);
        return result;
    }
}
