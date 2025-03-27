package org.ilmostro.basic.version;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UpdateUtilTests {


    @Test
    void test_compare_version_should_work() throws Exception {
        final var version1 = "1.3.1";
        final var version2 = "1.1.1";
        final var compare = UpdateUtil.compareVerNum(version1, version2);
        log.info("compare :{}", compare);
    }

    @Test
    void test_compare_sort_should_work() throws Exception {
        final var strings = new ArrayList<>(List.of("1.3.1", "1.2.1", "1.4.1", "1.2.2","1.23.1", "1.23.6.7", "1.2.1.2"));
        strings.sort((o1, o2) -> UpdateUtil.compareVerNum(o2, o1) - 2);
        log.info("sort version:{}", strings);
    }
}
