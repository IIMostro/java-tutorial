package org.ilmostro.pure.utils;

import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;
import java.util.Arrays;

public class ResolvableTypeUtils {


    @Test
    void test_get_class_resolvable_type() {

        final var interfaces = IntegerTestClass.class.getInterfaces();
        Arrays.stream(interfaces).map(ResolvableType::forClass)
                .map(ResolvableType::getGenerics)
                .flatMap(Arrays::stream)
                .forEach(System.out::println);
    }

    @Test
    void test_generic_class_resolvable_type()  throws Exception{
        final var genericInterfaces = IntegerTestClass.class.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            final var resolvableType = ResolvableType.forType(genericInterface);
            System.out.println(resolvableType.getGenerics()[0]);
        }
    }


    static interface TestClass<T extends Number> {
        T get();
    }

    static class IntegerTestClass implements TestClass<Integer> {

        @Override
        public Integer get() {
            return null;
        }
    }

    static class LongTestClass implements TestClass<Long> {

        @Override
        public Long get() {
            return null;
        }
    }
}
