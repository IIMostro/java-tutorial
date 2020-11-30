package org.ilmostro.user.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author IlMostro
 * @date 2019-11-05 23:15
 */
@Data
@ConfigurationProperties(prefix = "org.ilmostro.cache")
public class CustomRedisProperties {

    private BasicRedisProperties department;
    private BasicRedisProperties user;
}
