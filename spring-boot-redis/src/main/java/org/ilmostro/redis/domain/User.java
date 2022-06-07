package org.ilmostro.redis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author li.bowei
 * @date 2020/7/22 14:33
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private static final Random random = new Random();

	private Integer id;
	private String name;
	private int sex;
	private Integer age;
	private String password;
	private List<Score> scores;

	public void setNameAndAge(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public static User supplier() {
		return supplier(true);
	}

	public static User supplier(boolean score) {
		return new User(random.nextInt(100),
				RandomStringUtils.random(3, true, false),
				new Random().nextBoolean() ? 1 : 0,
				random.nextInt(60),
				UUID.randomUUID().toString(),
				score ? Stream.generate(Score::supplier).limit(10).collect(Collectors.toList()) : Collections
						.emptyList());
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Score implements Serializable {

		public static String[] subjects = new String[] {"语文", "数学", "英语"};
		private String subject;
		private BigDecimal score;

		public static Score supplier() {
			return new Score(subjects[new Random().nextInt(subjects.length)],
					BigDecimal.valueOf(Math.random() * 100));
		}
	}

}
