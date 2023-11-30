package org.ilmostro.basic.datastruct.bitmap;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.BitSet;

@Slf4j
public class JavaBitSetTests {

    @Test
    void test_set_get_first_zero(){
        // 创建两个 BitSet 对象
        java.util.BitSet firstBitSet = new java.util.BitSet();
        java.util.BitSet secondBitSet = new java.util.BitSet();

        // 设置第一个 BitSet 的位
        firstBitSet.set(0);
        firstBitSet.set(2);
        firstBitSet.set(4);

        // 设置第二个 BitSet 的位
        secondBitSet.set(1);
        secondBitSet.set(2);
        secondBitSet.set(3);

        // 创建一个新的 BitSet 用于存储结果
        java.util.BitSet result = new java.util.BitSet();

        // 遍历第一个 BitSet，检查是否在第二个 BitSet 中对应位为 0
        for (int i = firstBitSet.nextSetBit(0); i >= 0; i = firstBitSet.nextSetBit(i + 1)) {
            if (!secondBitSet.get(i)) {
                result.set(i);
            }
        }

        // 输出结果
        System.out.println("First BitSet: " + firstBitSet);
        System.out.println("Second BitSet: " + secondBitSet);
        System.out.println("Result BitSet: " + result);
    }


    @Test
    void test_bit_set_for_each(){
        final var bit = new BitSet();
        bit.set(1);
        bit.set(2);
        bit.set(5);
        bit.set(100);
        bit.stream().forEach(v1 -> log.info("index:{}", v1));
    }

    @Test
    void test_bit_xor_should_work(){
        final var bit = new BitSet();
        bit.set(1);
        bit.set(2);
        bit.set(3);

        final var bit1 = new BitSet();
        bit1.set(2);
        bit1.set(3);
        bit1.set(4);

        final var copy = bit.clone();
        bit.andNot(bit1);
//        bit.xor((BitSet) copy);
        log.info("bit: [{}]", bit);
    }

    @Test
    void test_bit_array_should_work(){
    }
}
