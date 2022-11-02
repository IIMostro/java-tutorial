package org.ilmostro.pure.service.impl;

import org.ilmostro.pure.annotation.Logger;
import org.ilmostro.pure.annotation.LoggerSupport;
import org.ilmostro.pure.service.HelloService;

import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
@LoggerSupport
public class HelloServiceImpl implements HelloService {

	@Override
	@Logger
	public String hello() {
		return "hello";
	}
}
