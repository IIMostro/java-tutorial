package org.ilmostro.basic.classloader;

/**
 * @author li.bowei
 **/
public class ClassLoadOrder {

    public static void main(String[] args) {
        MyClass instance = MyClass.getInstance();
        System.out.println("class a:"+ instance.getA());
        System.out.println("class b:"+ instance.getB());

        System.out.println(ConstantClass.KEY);
    }
}
