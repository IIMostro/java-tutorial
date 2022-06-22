package org.neptunus.springcloudsample.service;

import lombok.extern.slf4j.Slf4j;
import org.neptunus.springcloudsample.configuration.RefreshProperties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class LongTimeService implements InitializingBean {

	private final RefreshProperties properties;

	public LongTimeService(RefreshProperties properties) {
		this.properties = properties;
	}

	public String time() {
		log.info("time start:[{}]", System.currentTimeMillis());
//		TimeUnit.SECONDS.sleep(10);
		log.info("time end:[{}]", System.currentTimeMillis());
		return properties.getName();
	}


	@Override
	public void afterPropertiesSet() {
		log.info("long time service:[{}] loading success", hashCode());
	}
}
