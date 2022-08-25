package org.ilmostro.pure.controller;

import org.ilmostro.pure.annotation.RequestModel;
import org.ilmostro.pure.domain.GoodsElement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
@RequestMapping("/model")
public class RequestModelController {

	@PostMapping("/post")
	public String annotation(@RequestModel GoodsElement element){
		return element.toString();
	}
}
