package org.ilmostro.basic.clazz.classloader.hot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author li.bowei
 */
public class HotClassLoader extends ClassLoader {

	private final String projectPath;
	private final List<String> classList;

	public HotClassLoader(String projectPath, String... classPath) throws IOException {
		this.projectPath = projectPath.replace("%20", " ");
		this.classList = new ArrayList<>();
		for (String path : classPath) {
			path = path.replaceAll("\\.", "/").replaceAll("%20"," ");
			readFile(new File(path));
		}
	}

	private void readFile(File file) throws IOException {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				readFile(child);
			}
		}
		else {
			// 加载类
			String fileName = file.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!"class".equals(suffix)) {
				return;
			}
			//将class文件转为字节码
			InputStream inputStream = new FileInputStream(file);
			byte[] classByte = new byte[(int) file.length()];
			inputStream.read(classByte);
			// 获取全类名 com.xx.xx.class
			String className = this.getClassName(file);
			// 记录已被加载过的class文件
			classList.add(className);
			// 加载class到jvm虚拟机
			defineClass(className, classByte, 0, classByte.length);
		}
	}

	public String getClassName(File file) {
		String className = file.getPath().replace(projectPath, "");
		// 去掉.class
		className = className.substring(1, className.indexOf("."));
		// 将斜杠转为.
		className = className.replaceAll("/", ".");
		return className;
	}

	// 这个方法可改可不改
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> loadedClass = findLoadedClass(name);
		if (loadedClass == null) {
			if (classList.contains(name)) {
				throw new ClassNotFoundException("找不到类");
			}
			// 调用系统类加载器
			loadedClass = getSystemClassLoader().loadClass(name);
		}
		return loadedClass;
	}
}
