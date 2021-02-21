package org.ilmostro.start.service.process;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.start.service.condition.ActiveService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 **/
@Component
@Slf4j
public class CustomBeanPostProcess implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(ActiveService.class.isAssignableFrom(bean.getClass())){
            log.info("CustomBeanPostProcess postProcessBeforeInitialization bean name:{}", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(ActiveService.class.isAssignableFrom(bean.getClass())){
            log.info("CustomBeanPostProcess postProcessAfterInitialization bean name:{}", beanName);
        }
        return bean;
    }
}
