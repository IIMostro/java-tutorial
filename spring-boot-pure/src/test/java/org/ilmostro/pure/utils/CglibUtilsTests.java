package org.ilmostro.pure.utils;

import java.util.Arrays;

import org.ilmostro.pure.statemachine.OrderEntity;
import org.junit.jupiter.api.Test;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @author li.bowei
 */
public class CglibUtilsTests {

	@Test
	void test(){
		OrderEntity entity = new OrderEntity();
		entity.setOrderId(1L);

		final Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(entity.getClass());
		enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
			System.out.println(method);
			System.out.println(Arrays.toString(objects));
			System.out.println(methodProxy.getSuperName());
			return method.invoke(entity, objects);
		});
		final OrderEntity o = (OrderEntity)enhancer.create();
		System.out.println(o.getOrderId());
	}
}
