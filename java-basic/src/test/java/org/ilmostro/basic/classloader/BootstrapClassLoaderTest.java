package org.ilmostro.basic.classloader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * @author li.bowei
 * @date 2020/8/27 10:02
 */
@Slf4j
public class BootstrapClassLoaderTest {

    /**
     * <p>
     *     jvm虚拟机入口应用，在此可查看到各个classloader启动的顺序。
     *     第一个启动的是bootstrap classloader, 可以通过下面的方法获取到路径
     * </p>
     * @see sun.misc.Launcher
     */
    @Test
    public void bootstrapPath(){
        String message = System.getProperty("sun.boot.class.path");
        print(message);
    }

    /**
     * <p>
     *     第二个启动的是ext classloader
     * </p>
     * @see sun.misc.Launcher.ExtClassLoader
     */
    @Test
    public void extraPath(){
        String message = System.getProperty("java.ext.dirs");
        print(message);
    }

    /**
     * <p>
     *     第三个启动 app classloader
     * </p>
     * @see sun.misc.Launcher.AppClassLoader
     */
    @Test
    public void appPath(){
        String message = System.getProperty("java.class.path");
        print(message);
    }

    private void print(String message){
        String[] split = StringUtils.split(message, ";");
        for(String var1: split){
            log.info(var1);
        }
    }

    @Test
    public void getCurrentClassLoader(){
        log.info("this class classloader is {}",BootstrapClassLoaderTest.class.getClassLoader().toString());
        log.info("this class classloader parent is {}",BootstrapClassLoaderTest.class.getClassLoader().getParent().toString());
    }
}
