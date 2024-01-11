package org.ilmostro.basic.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class FibFunctionTest {

  @FunctionalInterface
  interface FibFunction {
    int call(int i);
  }

  FibFunction fib;

  @Test
  public void fib() {
    fib = n -> n == 0 ? 0 : n == 1 ? 1 : fib.call(n - 1) + fib.call(n - 2);
    for (int i = 0; i < 10; i++) {
      log.info("i:{}, fib:{}", i, fib.call(i));
    }
  }
}
