package org.ilmostro.basic.bitmap;

import io.vavr.collection.BitSet;
import io.vavr.collection.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@Slf4j
public class VavrBitSetTests {

    @Test
    void test_bit_set(){
        final var bitSet = BitSet.empty();
        bitSet.add(2);
        bitSet.peek(v1 -> log.info("v1: {}", v1));
    }

    @Test
    void test_java_bit_set(){
        final var bitSet = new java.util.BitSet();
        bitSet.set(1);
        bitSet.set(10000);
        bitSet.stream().forEach(v1 -> log.info("{}", v1));

        final var bitSet1 = new java.util.BitSet();
        bitSet1.set(2);
        bitSet1.or(bitSet);
        bitSet1.stream().forEach(v1 -> log.info("{}", v1));
    }

    @Test
    void test_set(){
        final var of = HashSet.of(1);
        final var of1 = HashSet.of(2);
        log.info("union {}", of.union(of1));
        log.info("intersect {}", of.intersect(of1));
        log.info("diff {}", of.diff(of1));
    }

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
    void test_bit_for_each(){
        final var bit = BitSet.of(1, 2, 3, 100);
        bit.forEach(v1 -> log.info("index:[{}]", v1));

        final var empty = BitSet.empty();
        empty.add(1);
    }

    @Test
    void test_bit_intersect(){
        final var bit1 = BitSet.of(1, 3, 5);
        final var bit2 = BitSet.of(3, 5, 6);
        log.info("bit1 intersect bit2: {}", bit1.intersect(bit2));
        log.info("bit2 intersect bit1: {}", bit2.intersect(bit1));
        log.info("bit1 diff bit2: {}", bit1.diff(bit2));
        log.info("bit2 diff bit1: {}", bit2.diff(bit1));
        log.info("bit1 union bit2: {}", bit1.union(bit2));

        bit1.map(Objects::toString).forEach(log::info);

        log.info("{}", BitSet.ofAll(1, 2,3));
    }

    @Test
    void test_bit_scan(){
        final var bit1 = BitSet.of(1, 3, 5);
        if (bit1.filter(v1 -> v1==2).isEmpty()) {
            log.info("empty");
        } else{
            log.info("not empty");
        }
    }

    @Test
    void test_bit_empty_add_should_work(){
        final var bit = BitSet.empty();
        log.info("bit: {}", bit.add(2).add(3).add(10000));
    }
}
