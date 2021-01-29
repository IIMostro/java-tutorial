package org.ilmostro.basic.builder;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author li.bowei
 **/
@Slf4j
public class CommonBuilderTest {

    @Test
    public void builder(){
        User build = CommonBuilder.of(User::new)
                .with(User::setId, 1)
                .with(User::setNameAndAge, "ilmostro", 3)
                .build();
        log.info("build entity is :{}", build);
    }
}