package org.ilmostro.pure.statemachine;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnStateMachineError;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
@WithStateMachine
@Slf4j
public class OrderStatusListener {

	@Autowired
	private StateMachine<OrderStatus, OrderStatusChangeEvent> machine;

	@OnTransition(source = "UNPAID", target = "PAID")
	public boolean paidTransition(Message<OrderStatusChangeEvent> message){
		final OrderStatusChangeEvent payload = message.getPayload();
		final Object order = message.getHeaders().get("order");
		log.info("unpaid -> paid event:[{}], order:[{}]", payload, order);
		return true;
	}

	@OnTransition(source = "UNPAID", target = "PAID")
	public boolean paidTwoTransition(Message<OrderStatusChangeEvent> message){
		final OrderStatusChangeEvent payload = message.getPayload();
		final Object order = message.getHeaders().get("order");
		log.info("unpaid -> paid event:[{}], order:[{}] two receive", payload, order);
		return true;
	}

	@OnTransition(source = "UNPAID", target = "CANCEL")
	public boolean cancelTransition(Message<OrderStatusChangeEvent> message){
		final OrderStatusChangeEvent payload = message.getPayload();
		final Object order = message.getHeaders().get("order");
		log.info("unpaid -> cancel event:[{}], order:[{}]", payload, order);
		return true;
	}

	@OnTransition(source = "PAID", target = "APPROVE")
	public boolean approveTransition(Message<OrderStatusChangeEvent> message){
//		final OrderStatusChangeEvent payload = message.getPayload();
//		final Object order = message.getHeaders().get("order");
//		log.info("paid -> approve event:[{}], order:[{}]", payload, order);
//		return true;
		try{
			throw new RuntimeException("sssss");
		} catch (Exception exception){
			machine.getExtendedState().getVariables().put("order", message.getHeaders().get("order"));
			machine.setStateMachineError(exception);
		}
		return false;
	}

	@OnStateMachineError
	public void approveTransitionError(ExtendedState state){
		final Map<Object, Object> variables = state.getVariables();
		log.info("get exception {}", state);
	}


	@OnTransition(source = "APPROVE", target = "REFUND")
	public boolean refundTransition(Message<OrderStatusChangeEvent> message){
		final OrderStatusChangeEvent payload = message.getPayload();
		final Object order = message.getHeaders().get("order");
		log.info("approve -> refund event:[{}], order:[{}]", payload, order);
		return true;
	}
}
