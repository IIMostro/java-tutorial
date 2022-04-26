package org.ilmostro.pure.processor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.ilmostro.pure.configuration.VertxSpringFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class VerticleBeanPostProcessor implements BeanPostProcessor {

    private final Vertx vertx;
    private final VertxSpringFactory factory;

    public VerticleBeanPostProcessor(Vertx vertx, VertxSpringFactory factory) {
        this.vertx = vertx;
        this.factory = factory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof AbstractVerticle){
            vertx.deployVerticle(factory.prefix() + ":" + bean.getClass().getName(), new DeploymentOptions().setWorker(true));
        }
        return bean;
    }
}
