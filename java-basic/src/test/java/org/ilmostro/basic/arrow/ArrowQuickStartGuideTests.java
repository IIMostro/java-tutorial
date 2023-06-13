package org.ilmostro.basic.arrow;

import lombok.extern.slf4j.Slf4j;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.IntVector;
import org.junit.jupiter.api.Test;

@Slf4j
public class ArrowQuickStartGuideTests {

    @Test
    void test_quick_start_guide() {
        try (final var allocator = new RootAllocator();
             final var intVector = new IntVector("fixed-size-primitive", allocator)) {

            intVector.allocateNew(3);
            intVector.set(0, 1);
            intVector.setNull(1);
            intVector.set(2, 2);
            intVector.setValueCount(3);
            log.info("intVector:{}", intVector);
        }
    }
}
