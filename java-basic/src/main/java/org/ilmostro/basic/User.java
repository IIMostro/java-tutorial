package org.ilmostro.basic;

import java.math.BigDecimal;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class User implements Comparable<User>{

    private Integer id;
    private String name;
    private int sex;
    private Integer age;
    private String password;
    private List<Score> scores;

    public void setNameAndAge(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public User say(){
        log.info("i am {}", hashCode());
        return this;
    }

    public static User supplier() {
        List<Score> scores = Stream.generate(Score::supplier).limit(10).collect(Collectors.toList());
        return new User(new Random().nextInt(),
                RandomStringUtils.random(3),
                new Random().nextBoolean() ? 1 : 0,
                (int) (Math.random() * 100),
                UUID.randomUUID().toString(),
                scores);
    }

    @Override
    public int compareTo(User o) {
        return this.id - o.id;
    }

    @Data
    @AllArgsConstructor
    public static class Score{

        public static String[] subjects = new String[]{"语文","数学","英语"};
        private String subject;
        private BigDecimal score;

        public static Score supplier(){
            return new Score(subjects[new Random().nextInt(subjects.length)],
                    BigDecimal.valueOf(Math.random() * 100));
        }
    }

}
