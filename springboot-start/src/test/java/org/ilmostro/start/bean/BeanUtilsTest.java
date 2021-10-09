package org.ilmostro.start.bean;

import org.apache.commons.beanutils.BeanUtils;
import org.ilmostro.start.entity.User;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author li.bowei
 */
public class BeanUtilsTest {

    @Test
    public void test() throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        user.setId(1);
        user.setName("neptune");

        BeanUtils.setProperty(user, "a", "this is a");
        System.out.println(user);
    }
}
