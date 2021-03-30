package org.ilmostro.basic.classloader;

/**
 * @author li.bowei
 **/
public class MyClass {

    private static final MyClass myclass = new MyClass();

    private static int a = 0;
    private static int b;

    public MyClass() {
        a++;
        b++;
    }

    public static MyClass getInstance(){
        return myclass;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
