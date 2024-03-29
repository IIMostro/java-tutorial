package org.ilmostro.basic.datastruct.option;

import io.vavr.Tuple;
import java.util.Optional;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 * @date 2020/7/23 17:29
 */
@Slf4j
public class OptionalTest {

  @Test
  public void test() {
    Random random = new Random();
    for (int index = 0; index < 100; index++) {
      log.info("random: {}", random.nextInt(10));
    }
  }

  @Test
  public void multi() {
    String s =
        Optional.of("a,b,c").map(v1 -> Tuple.of(v1, v1.split(v1))).map(v1 -> v1._1).orElse("");
    log.info("s:{}", s);
  }

  @Test
  public void lazy() {
    User user = new User();
    Optional<User> optional = Optional.of(user).map(User::say);
    log.info("after");
    optional.get();
  }
}
