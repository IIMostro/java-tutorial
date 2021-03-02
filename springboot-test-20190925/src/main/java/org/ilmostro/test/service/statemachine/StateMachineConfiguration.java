//package org.ilmostro.test.service.statemachine;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.statemachine.config.EnableStateMachine;
//import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
//import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
//
///**
// * @author li.bowei
// **/
//@Configuration
//@EnableStateMachine
//public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<PayStatus, PayEvent> {
//
//    @Override
//    public void configure(StateMachineTransitionConfigurer<PayStatus, PayEvent> transitions) throws Exception {
//        transitions.withExternal()
//                .source(PayStatus.UNPAID).target(PayStatus.CLOSE).event(PayEvent.CLOSE)
//                .and().withExternal()
//                .source(PayStatus.UNPAID).target(PayStatus.PAID).event(PayEvent.PAY)
//                .and().withExternal()
//                .source(PayStatus.PAID).target(PayStatus.REFUND).event(PayEvent.REFUND);
//    }
//}
