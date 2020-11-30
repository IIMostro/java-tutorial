package org.ilmostro.test.template.singleton;

import lombok.extern.slf4j.Slf4j;

/**
 * @author IlMostro
 * @date 2019-10-20 13:59
 * @description 使用枚举来创建单例模式，从JVM层次上保证线程的绝对安全
 */
@Slf4j
public class EnumsSingleton {

    private EnumsSingleton(){

    }

    public EnumsSingleton getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton{
        /**
         * 单例
         */
        INSTANCE;

        private EnumsSingleton singleton;

        Singleton(){
            singleton = new EnumsSingleton();
        }

        public EnumsSingleton getInstance(){
            return singleton;
        }
    }

    public static void main(String[] args) {
        EnumsSingleton instance = new EnumsSingleton().getInstance();
        EnumsSingleton instance1 = new EnumsSingleton().getInstance();
        EnumsSingleton instance2 = new EnumsSingleton().getInstance();
        EnumsSingleton instance3 = new EnumsSingleton().getInstance();

        log.info("var1 {}, var2 {}, var3 {}, var4{}", instance.hashCode(), instance1.hashCode(), instance2.hashCode(), instance3.hashCode());
    }
}
