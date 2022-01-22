package org.ilmostro.quarkus.service;

import io.quarkus.redis.client.RedisClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;

/**
 * @author li.bowei
 */
@ApplicationScoped
public class RedisClientService {

    @Inject
    RedisClient client;

    public String read(String key){
        return client.get(key).toString();
    }

    public void set(String key, String value){
        client.set(Arrays.asList(key, value));
    }
}
