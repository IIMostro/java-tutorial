package org.ilmostro.redis.hash;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.RedisApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.util.Map;

@SpringBootTest(classes = RedisApplication.class)
@Slf4j
public class ConsistentHashingTests {

    @Autowired
    private ConsistentHashing hashing;

    @Test
    void test_hashing_should_work() throws Exception{
        for (int i = 0; i < 10000; i++) {
            final var nodeName = "test" + i;
            hashing.getNode(nodeName);
        }
        final String localhost = InetAddress.getLocalHost().getHostAddress();
        final Map<String, Integer> nodeCount = hashing.getNodeCount(localhost);
        log.info("node count:{}", nodeCount);
    }
}
