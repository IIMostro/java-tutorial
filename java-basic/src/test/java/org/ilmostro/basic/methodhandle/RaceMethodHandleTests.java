package org.ilmostro.basic.methodhandle;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class RaceMethodHandleTests {


    static class Horse {
        public void race() {
            System.out.println("Horsey is racing!");
        }

        public void race(String name){
            System.out.println(name + " is racing!");
        }

        public void race(String name, String type){
            System.out.println(name + " is racing with type " + type);
        }

        private void race(Integer name){
            System.out.println("Horsey private is racing!");
        }
    }

    @Test
    void test_method_handle_should_work() throws Throwable {
        Horse horse = new Horse();
        final var mh = MethodHandles.lookup().findVirtual(Horse.class, "race", MethodType.methodType(void.class));
        mh.invoke(horse);
    }

    @Test
    void test_method_handle_with_argument_should_work() throws Throwable {
        Horse horse = new Horse();
        final var mh = MethodHandles.lookup().findVirtual(Horse.class, "race", MethodType.methodType(void.class, String.class));
        mh.invokeExact(horse, "Horsey with name");
    }

    @Test
    void test_method_handle_with_multiple_arguments_should_work() throws Throwable {
        Horse horse = new Horse();
        final var mh = MethodHandles.lookup().findVirtual(Horse.class, "race", MethodType.methodType(void.class, String.class, String.class));
        mh.invokeExact(horse, "Horsey with name", "Horse");
    }

    @Test
    void test_method_handle_with_private_method_should_work() throws Throwable {
        Horse horse = new Horse();
        final var method = Horse.class.getDeclaredMethod("race", Integer.class);
        method.setAccessible(true);
        final var mh = MethodHandles.lookup().unreflect(method);
        mh.invoke(horse, 1);
    }
}
