package org.ilmostro.pure.statemachine;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;

/**
 * @author li.bowei
 */
@SpringBootTest
public class OrderStateMachineTests {

	@Autowired
	private StateMachine<OrderStatus, OrderStatusChangeEvent> machine;

	@Autowired
	private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, OrderEntity> persister;

	@Test
	void test() throws Exception {
		machine.start();
		OrderEntity order = new OrderEntity();
		order.setOrderId(123456L);
		order.setStatus(OrderStatus.UNPAID);
		Message<OrderStatusChangeEvent> message = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED)
				.setHeader("order", order).build();
		machine.sendEvent(message);
		persister.persist(machine, order);
		message = MessageBuilder.withPayload(OrderStatusChangeEvent.MANAGER)
				.setHeader("order", order).build();
		try{
			machine.sendEvent(message);
		}catch (RuntimeException ex){

		}
		final StateMachine<OrderStatus, OrderStatusChangeEvent> restore = persister.restore(machine, order);
		final State<OrderStatus, OrderStatusChangeEvent> state = restore.getState();

	}
}
