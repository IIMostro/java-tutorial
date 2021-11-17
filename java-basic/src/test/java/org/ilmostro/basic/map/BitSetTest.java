package org.ilmostro.basic.map;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.BitSet;

/**
 * @author li.bowei
 */
@Slf4j
public class BitSetTest {

    @Test
    public void test() {
        BitSet bitSet = new BitSet();
        BitSet bitSet1 = new BitSet();
        for (int i = 0; i < 200; i++) {
            bitSet.set(i);
        }

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                bitSet1.set(i);
            }
            bitSet.set(i);
        }
        bitSet.xor(bitSet1);

        log.info("{}", bitSet);

        log.info("{}", bitSet.get(19));
    }

    @Test
    public void test1() {
        //稀疏位图
        EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
    }
}
