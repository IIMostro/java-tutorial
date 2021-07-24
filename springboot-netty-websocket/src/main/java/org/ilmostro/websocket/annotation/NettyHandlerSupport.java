package org.ilmostro.websocket.annotation;

import io.netty.channel.ChannelHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 */
@Component
public class NettyHandlerSupport implements BeanPostProcessor {

    private final MultiValueMap<NettyHandler, ChannelHandler> register = new LinkedMultiValueMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        NettyHandler annotation = AnnotationUtils.findAnnotation(bean.getClass(), NettyHandler.class);
        if(Objects.isNull(annotation) || !(bean instanceof ChannelHandler)){
            return bean;
        }
        register.add(annotation, (ChannelHandler) bean);
        return bean;
    }

    public List<String> getCustomPipelinePath(){
        if(register.isEmpty()){
            return Collections.emptyList();
        }
        return register.keySet().stream().map(NettyHandler::path).collect(Collectors.toList());
    }

    public List<ChannelHandler> getHandlers(String key){
        if(register.isEmpty()){
            return Collections.emptyList();
        }
        NettyHandler nettyHandler = getPath(key);
        if(Objects.isNull(nettyHandler)){
            return Collections.emptyList();
        }
        return register.get(nettyHandler);
    }

    private NettyHandler getPath(String key){
        return register.keySet().stream().filter(var1 -> var1.path().equals(key)).findAny().orElse(null);
    }
}
