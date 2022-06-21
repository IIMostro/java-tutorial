package org.neptunus.springcloudsample.controller;

import org.neptunus.springcloudsample.configuration.RefreshProperties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
public class PropertiesController {

	private final RefreshProperties properties;

	public PropertiesController(RefreshProperties properties) {
		this.properties = properties;
	}

	@GetMapping("/properties")
	public RefreshProperties getProperties(){
		return properties;
	}
}
