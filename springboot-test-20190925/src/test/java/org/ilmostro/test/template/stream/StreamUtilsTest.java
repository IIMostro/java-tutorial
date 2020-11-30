package org.ilmostro.test.template.stream;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
@Slf4j
public class StreamUtilsTest {

    private Collection<Integer> var1;

    private Collection<UserEntity> var2;

    @Before
    public void before(){
        this.var1 = StreamUtils.generate();
        this.var2 = StreamUtils.getUsers();
    }

    @Test
    public void generate() {
        var1.forEach(var1 -> log.info(var1.toString()));
    }

    @Test
    public void getUsers() {
        var2.forEach(var1 -> log.info(var1.toString()));
    }
}