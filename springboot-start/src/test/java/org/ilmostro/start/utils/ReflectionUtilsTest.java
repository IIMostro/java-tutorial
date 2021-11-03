package org.ilmostro.start.utils;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.start.service.generic.GenericService;
import org.ilmostro.start.service.generic.StringGenericServiceImpl;
import org.junit.Test;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@Slf4j
public class ReflectionUtilsTest {

    @Test
    public void test() {
        List<GenericService> services = Collections.singletonList(new StringGenericServiceImpl());
        Method superMethod = ReflectionUtils.getAllDeclaredMethods(GenericService.class)[0];
        for (GenericService service : services) {
            Method method = Optional.of(service)
                    .map(Object::getClass)
                    .map(ReflectionUtils::getAllDeclaredMethods)
                    .map(Stream::of)
                    .orElseGet(Stream::empty)
                    .filter(var1 -> var1.getName().equals(superMethod.getName()) && var1.getParameterCount() == superMethod.getParameterCount())
                    .findFirst().orElseThrow(RuntimeException::new);
            ResolvableType resolvableType = ResolvableType.forMethodParameter(method, 0);
            Class<?> resolve = resolvableType.resolve();
            log.info(resolve.getSimpleName());
        }
    }
}
