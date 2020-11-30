package org.ilmostro.basic.function;

import org.ilmostro.basic.User;

import java.util.Map;

/**
 * @author li.bowei
 * @date 2020/7/23 11:22
 */
@FunctionalInterface
public interface CustomFunction {

    Map<String, Object> generator(User user);
}
