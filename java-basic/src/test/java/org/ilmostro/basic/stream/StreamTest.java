package org.ilmostro.basic.stream;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.ilmostro.basic.executor.ForkJoinFactory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei
 * @date 2020/7/23 14:36
 */
@Slf4j
public class StreamTest {

    private final List<User> users = Stream.generate(User::supplier).skip(10).limit(20).collect(Collectors.toList());
    private final ForkJoinPool forkJoinPool = ForkJoinFactory.getForkJoinPool(10);

    @Before
    public void before() {
        log.info("origin users");
        for (User user : users) {
            log.info(user.toString());
        }
    }

    @Test
    public void generator(){
        Stream<User> limit = Stream.generate(User::supplier).limit(10000);
        List<User> collect = limit.collect(Collectors.toList());
        List<Integer> collect1 = limit.map(User::getAge).collect(Collectors.toList());
    }

    @Test
    public void collect(){
        Map<String, User> collect = users.stream().collect(Collectors.toMap(User::getPassword, v1 -> v1, (v1, v2) -> v1));
    }

    @Test
    public void parallel() {
//        users.stream().parallel()
//                .forEach(var1 -> log.info("for each:{}",Thread.currentThread().getName()));
//        log.info("----------------------------------");
//        ForkJoinTask<?> submit = forkJoinPool.submit(() -> users.stream().parallel().forEach(var1 -> log.info(Thread.currentThread().getName())));
//        submit.join();

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer integer = integers.stream().reduce(Math::multiplyExact).orElse(0);
        Integer integer1 = integers.stream().min(Integer::compareTo).orElse(null);
        boolean b = integers.stream().anyMatch(var1 -> var1.equals(1));
        Integer integer2 = integers.stream().filter(var1 -> var1 > 4).findAny().orElse(null);
        log.info("sum:{}", integer);
        log.info("min:{}", integer1);
    }


    @Test
    public void simple() {

    }

    @Test
    public void originFilter(){
        List<User> collect = new LinkedList<>();
        for(User user: users){
            if(user.getAge() <= 18){
                continue;
            }
            collect.add(user);
        }
        collect.forEach(var1 -> log.info(var1.toString()));
    }

    @Test
    public void filter(){
        users.stream()
                .filter(var1 -> var1.getAge() > 18)
                .map(User::toString)
                .forEach(log::info);
    }

    @Test
    public void originMap(){
        List<String> collect = new LinkedList<>();
        for(User user: users){
            collect.add(user.getName());
        }
    }

    @Test
    public void map(){
        List<String> collect = users.stream().map(User::getName).collect(Collectors.toList());
        collect.forEach(log::info);
    }

    @Test
    public void originCount() {
        Map<String, BigDecimal> collect = new HashMap<>(User.Score.subjects.length);
        for (User user : users) {
            List<User.Score> scores = user.getScores();
            for (User.Score score : scores) {
                String subject = score.getSubject();
                BigDecimal bigDecimal = collect.get(subject);
                if (Objects.nonNull(bigDecimal)) {
                    collect.put(subject, bigDecimal.add(score.getScore()));
                } else {
                    collect.put(subject, score.getScore());
                }
            }
        }
        for (Map.Entry<String, BigDecimal> entry : collect.entrySet()) {
            log.info("key:{}", entry.getKey());
            log.info("value:{}", entry.getValue());
        }
    }

    @Test
    public void count() {
        Map<String, BigDecimal> collect = users.stream()
                .map(User::getScores)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(User.Score::getSubject, User.Score::getScore, BigDecimal::add));
        for (Map.Entry<String, BigDecimal> entry : collect.entrySet()) {
            log.info("key:{}", entry.getKey());
            log.info("value:{}", entry.getValue());
        }
    }
}
