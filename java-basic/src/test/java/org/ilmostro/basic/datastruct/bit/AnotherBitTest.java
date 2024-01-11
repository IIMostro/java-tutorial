package org.ilmostro.basic.datastruct.bit;

import java.util.BitSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class AnotherBitTest {

  @Test
  public void test() {
    int number = 0b1101;
    log.info("{}", Integer.toBinaryString(number & ~(1 << 3)));
  }

  @Test
  public void test1() {
    BitSet bitSet = new BitSet();
    bitSet.set(100);

    for (int i = 0; i < 99; i++) {
      log.info("{}", bitSet.get(i));
    }
  }
}
