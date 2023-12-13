package org.ilmostro.basic.clazz.reflection;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import sun.misc.Unsafe;

/**
 * @author li.bowei
 */
public class UnSafeAction {

	/**
	 * 使用安全模型获取Unsafe
	 *
	 * @return Unsafe
	 * @throws PrivilegedActionException 错误
	 */
	public static Unsafe getInstance() throws PrivilegedActionException {
		return AccessController.doPrivileged((PrivilegedExceptionAction<Unsafe>) () -> {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			return (Unsafe) theUnsafe.get(null);
		});
	}
}
