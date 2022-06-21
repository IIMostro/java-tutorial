package org.neptunus.springcloudsample.configuration;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author li.bowei
 */
@ConfigurationProperties(prefix = "application")
@Data
public class RefreshProperties {

	private String name;
	private Integer age;
}
