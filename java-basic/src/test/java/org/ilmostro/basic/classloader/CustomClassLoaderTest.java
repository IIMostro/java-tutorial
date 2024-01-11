package org.ilmostro.basic.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.ilmostro.basic.clazz.classloader.CustomClassLoader;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 * @date 2020/8/27 10:47
 */
public class CustomClassLoaderTest {

  @Test
  public void test()
      throws ClassNotFoundException,
          IllegalAccessException,
          InstantiationException,
          NoSuchMethodException,
          InvocationTargetException {
    CustomClassLoader customClassLoader = new CustomClassLoader("d:\\workspaces\\idea");
    Class<?> hello = customClassLoader.loadClass("hello");
    Object o = hello.newInstance();
    Method say = hello.getDeclaredMethod("say");
    say.invoke(o);
  }
}
