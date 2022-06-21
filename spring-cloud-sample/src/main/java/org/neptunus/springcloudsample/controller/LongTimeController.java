package org.neptunus.springcloudsample.controller;

import org.neptunus.springcloudsample.service.LongTimeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
public class LongTimeController {

	private final LongTimeService service;

	public LongTimeController(LongTimeService service) {
		this.service = service;
	}

	@GetMapping("/long")
	public String execute() throws Exception{
		return service.time();
	}
}
