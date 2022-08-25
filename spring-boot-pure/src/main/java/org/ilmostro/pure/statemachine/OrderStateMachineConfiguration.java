package org.ilmostro.pure.statemachine;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

/**
 * @author li.bowei
 */
@Configuration
@EnableStateMachine
@Slf4j
public class OrderStateMachineConfiguration extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {

	private static final Map<Long, OrderEntity> STORE = new ConcurrentHashMap<>();

	@Override
	public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> model) throws Exception {
		model.withStates().initial(OrderStatus.UNPAID).states(EnumSet.allOf(OrderStatus.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
		transitions
				.withExternal().source(OrderStatus.UNPAID).target(OrderStatus.PAID).event(OrderStatusChangeEvent.PAYED)
				.and()
				.withExternal().source(OrderStatus.UNPAID).target(OrderStatus.CANCEL).event(OrderStatusChangeEvent.CANCEL)
				.and()
				.withExternal().source(OrderStatus.PAID).target(OrderStatus.APPROVE).event(OrderStatusChangeEvent.MANAGER)
				.and()
				.withExternal().source(OrderStatus.APPROVE).target(OrderStatus.REFUNDED).event(OrderStatusChangeEvent.FINANCE);
	}

	@Bean
	public DefaultStateMachinePersister<OrderStatus, OrderStatusChangeEvent, OrderEntity> persister(){
		return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderStatus, OrderStatusChangeEvent, OrderEntity>() {
			@Override
			public void write(StateMachineContext<OrderStatus, OrderStatusChangeEvent> context, OrderEntity order) throws Exception {
				log.info("order:[{}] status:[{}] context:[{}] will be store", order, context.getState(), context.getEvent());
				STORE.put(order.getOrderId(), order);
			}

			@Override
			public StateMachineContext<OrderStatus, OrderStatusChangeEvent> read(OrderEntity order) throws Exception {
				return new DefaultStateMachineContext<>(STORE.get(order.getOrderId()).getStatus(), null, null, null);
			}
		});
	}
}
