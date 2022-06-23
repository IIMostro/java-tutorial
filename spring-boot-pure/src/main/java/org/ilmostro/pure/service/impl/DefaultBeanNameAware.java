package org.ilmostro.pure.service.impl;

import org.ilmostro.pure.service.OrderInitService;

/**
 * @author li.bowei
 */
public class DefaultBeanNameAware implements OrderInitService {
	@Override
	public int order() {
		return 0;
	}

	@Override
	public void setBeanName(String s) {

	}

	@Override
	public int getOrder() {
		return 0;
	}
}
