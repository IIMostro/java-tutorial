package org.ilmostro.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author li.bowei
 */
public class LogAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation)  {

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> builder
                .method(ElementMatchers.any()) // 拦截任意方法
                .intercept(MethodDelegation.to(TimeInterceptor.class));

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {

            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }
        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("org.ilmostro.websocket.handler"))// 指定需要拦截的类
                .and(ElementMatchers.not(ElementMatchers.isInterface()))
                .and(ElementMatchers.not(ElementMatchers.isStatic()))
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);

    }
}
