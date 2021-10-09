package org.ilmostro.start.reflection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.CannotCompileException;
import org.ilmostro.start.entity.User;
import org.ilmostro.start.utils.DynamicObjectUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author li.bowei
 */
public class DynamicObjectUtilsTest {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test() throws CannotCompileException, IllegalAccessException, InstantiationException {
        Class<Object> objectClass = DynamicObjectUtils.prepareClass(User.class.getName(), Arrays.asList("a", "b"));
        Object o = objectClass.newInstance();
        System.out.println(o);
    }

    @Test
    public void test1() throws CannotCompileException {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("a", "this is a");
        parameter.put("b", "this is b");
        Class<Object> objectClass = DynamicObjectUtils.prepareClass(User.class.getName(), Arrays.asList("a", "b"));
        Object object = DynamicObjectUtils.createObject(objectClass, Arrays.asList("a", "b"), parameter);
    }

    @Test
    public void test3() throws JsonProcessingException {
        User user = new User();
        user.setId(1);
        user.setName("neptune");
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("a", "this is a");
        parameter.put("b", "this is b");
        Object target = DynamicObjectUtils.getTarget(user, parameter);
        System.out.println(objectMapper.writeValueAsString(target));
    }
}
