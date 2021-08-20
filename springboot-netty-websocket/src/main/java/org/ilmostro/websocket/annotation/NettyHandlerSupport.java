package org.ilmostro.websocket.annotation;

import io.netty.channel.ChannelHandler;
import org.ilmostro.websocket.handler.PathNotFoundHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 */
@Component
public class NettyHandlerSupport implements BeanPostProcessor {

    //一个path可以有多个channel handler来处理。并且是根据连接来确认处理的pipeline
    private final Map<NettyHandler, ChannelHandler> register = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) throws BeansException {
        NettyHandler annotation = AnnotationUtils.findAnnotation(bean.getClass(), NettyHandler.class);
        if(Objects.isNull(annotation) || !(bean instanceof ChannelHandler)){
            return bean;
        }
        register.put(annotation, (ChannelHandler) bean);
        return bean;
    }

    public List<String> getCustomPipelinePath(){
        if(register.isEmpty()){
            return Collections.emptyList();
        }
        return register.keySet().stream().map(NettyHandler::path).collect(Collectors.toList());
    }

    public ChannelHandler getHandler(String key){
        if(register.isEmpty()){
            return new PathNotFoundHandler();
        }
        NettyHandler nettyHandler = getPath(key);
        if(Objects.isNull(nettyHandler)){
            return new PathNotFoundHandler();
        }
        return register.get(nettyHandler);
    }

    private NettyHandler getPath(String key){
        return register.keySet().stream().filter(var1 -> var1.path().equals(key)).findAny().orElse(null);
    }
}
