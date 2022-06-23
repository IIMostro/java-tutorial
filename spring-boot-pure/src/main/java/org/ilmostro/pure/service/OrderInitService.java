package org.ilmostro.pure.service;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.Ordered;

/**
 * @author li.bowei
 */
public interface OrderInitService extends Ordered, BeanNameAware {

	int order();
}
