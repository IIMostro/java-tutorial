package org.ilmostro.start.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.lang.NonNull;

/**
 * @author li.bowei
 */
public abstract class RedisMessageHandlerAdapter implements BeanFactoryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessageHandlerAdapter.class);
    private final String topic = topic();

    protected abstract String topic();

    public void handleMessage(String content){
        logger.info("topic:{}, on message:{}", topic(), content);
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory factory) throws BeansException {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessageListenerAdapterWrapper.class, () -> {
            MessageListenerAdapterWrapper wrapper = new MessageListenerAdapterWrapper(this);
            wrapper.setTopic(topic);
            return wrapper;
        });
        BeanDefinition beanDefinition = builder.getRawBeanDefinition();
        ((DefaultListableBeanFactory) factory).registerBeanDefinition("messageListenerAdapterWrapper" + topic, beanDefinition);
    }
}
