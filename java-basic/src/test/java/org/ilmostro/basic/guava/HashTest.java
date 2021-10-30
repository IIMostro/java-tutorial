package org.ilmostro.basic.guava;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class HashTest {

    @Test
    public void test(){
        long a = Hashing.sha256().hashString("d", Charsets.UTF_8).asInt();
        log.info("{}", a);
    }

}
