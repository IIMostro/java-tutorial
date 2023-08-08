package org.ilmostro.redis.hash;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsistentHashing implements InitializingBean, DisposableBean {

    private final StringRedisTemplate redis;
    private static final String CONSISTENT_HASHING_KEY = "consistent:hashing:vm";
    private static final String CONSISTENT_HASHING_NODE_KEY = "consistent:hashing:node:";

    public Map<String, Integer> getNodeCount(String key) {
        return redis.<String, Integer>opsForHash().entries(CONSISTENT_HASHING_NODE_KEY + key);
    }


    public String getNode(String key) {
        final var hash = MurmurHash.hash(key);
        final BoundZSetOperations<String, String> operations = redis.boundZSetOps(CONSISTENT_HASHING_KEY);
        //noinspection DataFlowIssue
        final var slot = Optional.of(hash)
                .map(v1 -> operations.rangeByScoreWithScores(v1, Long.MAX_VALUE))
                .filter(v1 -> !CollectionUtils.isEmpty(v1))
                .or(() -> Optional.ofNullable(operations.rangeByScoreWithScores(0, hash)).filter(v1 -> !CollectionUtils.isEmpty(v1)))
                .orElseGet(Collections::emptySet)
                .stream()
                .min(Comparator.comparingDouble(ZSetOperations.TypedTuple::getScore))
                .map(ZSetOperations.TypedTuple::getValue)
                .orElse("");
        redis.boundHashOps(CONSISTENT_HASHING_NODE_KEY + localhost()).increment(slot, 1);
        return slot;
    }

    public static String localhost(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void destroy() throws Exception {
        final String localhost = InetAddress.getLocalHost().getHostAddress();
        final var operations = redis.<String, String>boundHashOps(CONSISTENT_HASHING_NODE_KEY + localhost);
        final var entries = operations.entries();
        if (CollectionUtils.isEmpty(entries)) {
            return;
        }
        redis.delete(CONSISTENT_HASHING_NODE_KEY + localhost);
        final BoundZSetOperations<String, String> zSetOperations = redis.boundZSetOps(CONSISTENT_HASHING_KEY);
        zSetOperations.remove(entries.keySet().toArray(new Object[0]));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final String localhost = InetAddress.getLocalHost().getHostAddress();
        final var nodeOperator = redis.<String, String>boundHashOps(CONSISTENT_HASHING_NODE_KEY + localhost);
        final BoundZSetOperations<String, String> operations = redis.boundZSetOps(CONSISTENT_HASHING_KEY);
        int replicas = 3;

        for (int i = 0; i < replicas; i++) {
            final String nodeName = localhost + "-VM" + i;
            operations.add(nodeName, BigDecimal.valueOf(Integer.MAX_VALUE).divide(BigDecimal.valueOf(replicas), 0, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(i)).doubleValue());
            nodeOperator.put(nodeName, "0");
        }
    }
}
