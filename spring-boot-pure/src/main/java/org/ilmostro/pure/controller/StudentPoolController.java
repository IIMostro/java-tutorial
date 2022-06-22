package org.ilmostro.pure.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.ilmostro.pure.domain.Student;
import org.slf4j.helpers.MessageFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 */
@RestController
@Slf4j
public class StudentPoolController {

	@GetMapping("/student")
	public String student(){
		final Student student = Student.POOL.get();
		student.init(RandomStringUtils.randomPrint(3), RandomUtils.nextInt());
		final String message = MessageFormatter
				.format("student:[{}], hash:[{}]", student.toString(), student.hashCode()).getMessage();
		log.info("student:[{}], hash:[{}]", student.toString(), student.hashCode());
		student.release();
		return message;
	}
}
