package org.ilmostro.pure.statemachine;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.event.OnStateMachineError;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
//@Component
@Slf4j
public class StateMachineErrorHandler implements ApplicationListener<OnStateMachineError> {

	@Override
	public void onApplicationEvent(OnStateMachineError event) {
		log.info("get exception:{}", event);
	}
}
