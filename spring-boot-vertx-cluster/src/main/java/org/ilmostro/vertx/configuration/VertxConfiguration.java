package org.ilmostro.vertx.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableConfigurationProperties(VertxProperties.class)
public class VertxConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(VertxConfiguration.class);
    private static final AtomicBoolean circle = new AtomicBoolean(false);

    @Bean
    @ConditionalOnProperty(prefix = "vertx", name = "type", havingValue = "hazelcast", matchIfMissing = true)
    public Vertx hazelcast(VertxSpringFactory factory) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:cluster.xml");
        Config config = new XmlConfigBuilder(new FileInputStream(file)).build();
        HazelcastClusterManager clusterManager = new HazelcastClusterManager(config);
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager);
        Future<Vertx> future = Vertx.clusteredVertx(options);
        logger.info("vertx cluster initing....");
        //自旋, 等待future完成
        //noinspection StatementWithEmptyBody
        while (circle.compareAndSet(false, future.isComplete())) {

        }
        logger.info("vertx cluster inited....");
        logger.info("vertx claster init finshed time :{}", System.currentTimeMillis());
        Vertx result = future.result();
        result.registerVerticleFactory(factory);
        return result;
    }


    @Bean
    @ConditionalOnProperty(prefix = "vertx", name = "type", havingValue = "zookeeper")
    public Vertx zookeeper(VertxSpringFactory factory,
                           VertxProperties properties) {
        JsonObject zkConfig = new JsonObject();
        zkConfig.put("zookeeperHosts", properties.getZookeeper().getHost());
        zkConfig.put("rootPath", properties.getZookeeper().getRootPath());
        zkConfig.put("sessionTimeout", properties.getZookeeper().getSessionTimeout());
        zkConfig.put("connectTimeout", properties.getZookeeper().getConnectTimeout());
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", properties.getZookeeper().getRetry().getInitialSleepTime())
                .put("maxTimes", properties.getZookeeper().getRetry().getMaxTimes()));
        ClusterManager clusterManager = new ZookeeperClusterManager(zkConfig);
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager);
        Future<Vertx> future = Vertx.clusteredVertx(options);
        logger.info("vertx cluster inited....");
        logger.info("vertx claster init finshed time :{}", System.currentTimeMillis());
        //自旋, 等待future完成
        //noinspection StatementWithEmptyBody
        while (circle.compareAndSet(false, future.isComplete())) {

        }
        Vertx result = future.result();
        result.registerVerticleFactory(factory);
        return result;
    }
}
