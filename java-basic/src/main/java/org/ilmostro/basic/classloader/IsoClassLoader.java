package org.ilmostro.basic.classloader;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * @author li.bowei
 */
public class IsoClassLoader extends ClassLoader {

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException{
		synchronized (getClassLoadingLock(name)) {
			Class<?> c = findLoadedClass(name);
			if (c == null) {
				try{
					c = findClass(name);
				}catch (ClassNotFoundException ex){
					c = Class.forName(name, false, getParent());
				}
			}
			if (resolve) {
				resolveClass(c);
			}
			return c;
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> clazz = null;
		String classFilename = getParent().getResource("").getPath()
				.concat(name.replace(".","/")
						.concat(".class"));
		File classFile = new File(classFilename);
		if (classFile.exists()) {
			try (InputStream is = new FileInputStream(classFile)) {
				final byte[] bytes = IOUtils.toByteArray(is);
				clazz = defineClass(name, bytes, 0, bytes.length);
			} catch (IOException e) {
				throw new ClassNotFoundException(name);
			}
		}
		if (clazz == null) {
			throw new ClassNotFoundException(name);
		}
		return clazz;
	}

	@Override
	public String toString() {
		return "IsoClassLoader";
	}
}
