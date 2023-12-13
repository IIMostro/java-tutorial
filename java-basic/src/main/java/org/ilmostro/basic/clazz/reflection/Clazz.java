package org.ilmostro.basic.clazz.reflection;

/**
 * @author li.bowei
 */
public class Clazz {

    public static void main(String[] args) {
        Class<Object> objectClass = Object.class;
        String name = objectClass.getName();
        System.out.println(name.equals("java.lang.Object"));
    }
}
