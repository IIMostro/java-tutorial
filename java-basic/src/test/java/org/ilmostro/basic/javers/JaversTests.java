package org.ilmostro.basic.javers;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.javers.core.JaversBuilder;
import org.junit.jupiter.api.Test;

@Slf4j
public class JaversTests {

    @Test
    void test_diff_should_work(){
        final var user = new User();
        user.setId(1);
        user.setName("ilmostro");

        final var user2 = new User();
        user2.setId(2);
        user2.setName("neptune");

        final var javers = JaversBuilder.javers().build();
        final var diff = javers.compare(user, user2);
        log.info("diff:{}", diff.prettyPrint());
    }
}
