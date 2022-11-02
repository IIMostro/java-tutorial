package org.ilmostro.pure.support;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.assertj.core.internal.bytebuddy.asm.Advice;

/**
 * @author li.bowei
 */
@Slf4j
public class LoggerServiceWrapper {

	@RuntimeType
	public static Object intercept(
//			@Argument(0) Object arg0,		//注入目标参数（）
			@Origin Method method,			//注入目标类方法
			@AllArguments Object[] args,	//注入目标方法参数列表
//			@This Object proxy,				//注入代理对象（不能直接调用，否则会死循环进入拦截方法）
			@Super Object source,			//注入目标对象
			@SuperCall Callable<?> callable	//注入可执行的目标方法
	) throws Exception {
		final Object result = callable.call();
		log.info("Bean [{}] invoke method:[{}] args:[{}] result:[{}]",
				source.getClass().getName(),
				method.getName(),
				args,
				result);
		return result;
	}
}
