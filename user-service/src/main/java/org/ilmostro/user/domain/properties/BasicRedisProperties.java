package org.ilmostro.user.domain.properties;

import lombok.Data;

/**
 * @author IlMostro
 * @date 2019-11-05 23:18
 */
@Data
public class BasicRedisProperties {

    private String key;
    private Long timeout;
}
