package org.ilmostro.basic.classloader;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author li.bowei
 **/
@Slf4j
public class ClassLoadOrder {

    public static void main(String[] args) throws Exception{
        classLoaderFrom();
    }

    public static void classLoaderFrom() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("org.ilmostro.basic.classloader.ClassLoadOrder");
        Object o = clazz.getConstructors()[0].newInstance();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.equals("classLoaderParent")){
                log.info("invoke method:{}", name);
                method.invoke(o);
            }
        }
        log.info("object class is :{}", o);
    }

    public static void newInstance(){
        MyClass instance = MyClass.getInstance();
        System.out.println("class a:"+ instance.getA());
        System.out.println("class b:"+ instance.getB());
    }

    public static void classLoaderParent() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader classLoader = ClassLoadOrder.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass("org.ilmostro.basic.classloader.MyClass");
        Constructor<?> constructor = aClass.getConstructors()[0];
        Object o = constructor.newInstance();
        log.info("object:{}", o);
    }
}
