package org.ilmostro.basic.function;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 * @date 2020/7/22 17:47
 */
@Slf4j
public class ConsumerTest {

  /** 简单函数 */
  @Test
  public void simple() {
    Consumer<String> consumer = log::info;
    consumer.accept("简单函数");
  }

  /** 组合的消费函数使用 */
  @Test
  public void and() {
    Consumer<String> consumer = log::info;
    Consumer<String> errorConsumer = log::error;
    Consumer<String> andThen = consumer.andThen(errorConsumer);
    andThen.accept("组合函数，消费顺序");
  }
}
