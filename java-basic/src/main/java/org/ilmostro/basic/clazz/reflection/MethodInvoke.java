package org.ilmostro.basic.clazz.reflection;

import java.lang.reflect.Method;

/**
 * @author li.bowei
 */
public class MethodInvoke {

    public Object invoke(Object object, Method method, Object... args) throws Exception {
        return object.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(object, args);
    }

    public void hello(String name){
        System.out.println(name);
    }

    public static void main(String[] args) throws Exception {
        MethodInvoke service = new MethodInvoke();
        Object hello = service.invoke(service, service.getClass().getMethod("hello", String.class), "hello");
        System.out.println(hello);
    }
}
