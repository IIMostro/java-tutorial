package org.ilmostro.test.service.statemachine;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author li.bowei
 **/
@WithStateMachine
public class BizService {

    @OnTransition()
    public void order(){

    }
}
