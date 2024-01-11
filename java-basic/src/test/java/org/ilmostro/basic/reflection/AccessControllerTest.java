package org.ilmostro.basic.reflection;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

/**
 * @author li.bowei
 */
public class AccessControllerTest {

  @Test
  public void test() throws PrivilegedActionException {
    Unsafe unsafe =
        AccessController.doPrivileged(
            (PrivilegedExceptionAction<Unsafe>)
                () -> {
                  Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                  theUnsafe.setAccessible(true);
                  return (Unsafe) theUnsafe.get(null);
                });
    System.out.println(unsafe);
  }
}
