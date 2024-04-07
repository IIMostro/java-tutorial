package org.ilmostro.redis.caching;

import org.ilmostro.redis.service.CachingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CachingServiceTests {

    @Autowired
    private CachingService cachingService;

    @Test
    void test_cacheable_should_work(){
        cachingService.get("bucket-test", "key");
    }
}
