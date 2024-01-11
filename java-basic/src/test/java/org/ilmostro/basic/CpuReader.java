package org.ilmostro.basic;

import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
public class CpuReader {

  @Test
  public void cpu() {
    int num = 0;
    long start = System.currentTimeMillis() / 1000;
    while (true) {
      num = num + 1;
      if (num == Integer.MAX_VALUE) {
        System.out.println("reset");
        num = 0;
      }
      if ((System.currentTimeMillis() / 1000) - start > 1000) {
        return;
      }
    }
  }
}
