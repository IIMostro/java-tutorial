package org.neptunus.springcloudsample.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author li.bowei
 */
@Configuration
@EnableConfigurationProperties(RefreshProperties.class)
public class RefreshPropertiesConfiguration {
}
