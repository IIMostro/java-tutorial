package org.ilmostro.start.function;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.start.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.function.Function;

/**
 * @author li.bowei
 * @date 2020/11/4 11:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class UserFunction {

    @Autowired
    private Function<User, String> function;

    @Test
    public void test(){
        User user = new User();
        user.setId(1);
        user.setName("ilmostro");
        user.setAge(18);

        String apply = function.apply(user);
        log.info("autowired function, function apply result:{}", apply);
    }
}
