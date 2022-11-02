package org.ilmostro.basic.buddy;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.alibaba.fastjson.JSON;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * @author li.bowei
 */
public class RunTypeDemo {

	@RuntimeType
	public static Object intercept(
			@Argument(0) Object arg0,		//注入目标参数（）
			@Origin Method method,			//注入目标类方法
			@AllArguments Object[] args,	//注入目标方法参数列表
			@This BizService proxy,				//注入代理对象（不能直接调用，否则会死循环进入拦截方法）
			@Super BizService source,			//注入目标对象
			@SuperCall Callable<?> callable	//注入可执行的目标方法
	) throws Exception {
		System.out.println("第一个参数为: "+ arg0);
		System.out.println("callable对象：" + callable);
		System.out.println("method对象：" + method);
		System.out.println("参数列表：" + JSON.toJSONString(args));
		System.out.println("代理对象：" + proxy);
		System.out.println("目标对象：" + source);
		//测试在拦截方法内直接调用源对象
		source.hello("YYY", "AAA");
		return callable.call();
	}
}
