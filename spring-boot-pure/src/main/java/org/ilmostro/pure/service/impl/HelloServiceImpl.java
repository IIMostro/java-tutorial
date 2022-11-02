package org.ilmostro.pure.service.impl;

import org.ilmostro.pure.annotation.LoggerWrapper;
import org.ilmostro.pure.service.HelloService;

import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
public class HelloServiceImpl implements HelloService {

	@Override
	@LoggerWrapper
	public String hello() {
		return "hello";
	}
}
