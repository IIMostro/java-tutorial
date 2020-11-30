//package org.ilmostro.user.aspect;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.ilmostro.user.enums.basic.MessageFlag;
//import org.ilmostro.user.stream.MessageProducer;
//import org.ilmostro.user.stream.UserMessageProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author li.bowei on 2019-10-29.
// * @description
// */
//@Aspect
//@Component
//@EnableBinding(UserMessageProducer.class)
//@Slf4j
//public class UserAspect {
//
//    private final UserMessageProducer producer;
//
//    @Autowired
//    public UserAspect(UserMessageProducer producer) {
//        this.producer = producer;
//    }
//
//    @Pointcut("execution(* org.ilmostro.user.service.user.UserService.save(..))")
//    public void save(){
//
//    }
//
//    @Around("save()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        sendMessage(Arrays.asList(pjp.getArgs()));
//        return pjp.proceed();
//    }
//
//    /**
//     * 消息发送
//     * @param objects 参数
//     */
//    private void sendMessage(List<Object> objects){
//        if(objects.isEmpty()){
//            throw new RuntimeException("参数不能为空");
//        }
//        Object o = objects.stream().findAny().orElse(null);
//        MessageProducer<Object> message = new MessageProducer<>();
//        message.setData(o);
//        message.setFlag(MessageFlag.INSERT);
//        producer.info().send(MessageBuilder.withPayload(message).build());
//    }
//}
