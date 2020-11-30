package org.ilmostro.start.service;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author li.bowei
 * @date 2020/10/9 13:57
 */
public class CustomCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        Map<String, Object> end = metadata.getAnnotationAttributes("End");
//        Object value = end.get("value");
//        if(value.equals(0)){
//            return  true;
//        }
        return false;
    }
}
