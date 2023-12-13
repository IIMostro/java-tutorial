package org.ilmostro.basic.clazz.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author li.bowei
 * @date 2020/8/27 10:35
 */
public class CustomClassLoader extends ClassLoader {

    private final String path;

    public CustomClassLoader(String path) {
        this.path = path;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);

        File file = new File(path, fileName);

        try (FileInputStream is = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            int len;
            while ((len = is.read()) != -1) {
                bos.write(len);
            }
            byte[] data = bos.toByteArray();
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private String getFileName(String name) {
        // TODO Auto-generated method stub
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        } else {
            return name.substring(index + 1) + ".class";
        }
    }
}
