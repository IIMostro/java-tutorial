package org.ilmostro.test.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei on 2019-10-09.
 * @description
 */
@Component
@ConfigurationProperties(prefix = "ilmostro")
@Data
public class RefreshProperties {

    private String name;
}
