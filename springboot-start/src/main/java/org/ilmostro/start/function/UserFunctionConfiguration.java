package org.ilmostro.start.function;

import org.ilmostro.start.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author li.bowei
 * @date 2020/11/4 11:07
 */
@Configuration
public class UserFunctionConfiguration {

    @Bean
    public Function<User, String> getUserName(){
        return User::getName;
    }
}
