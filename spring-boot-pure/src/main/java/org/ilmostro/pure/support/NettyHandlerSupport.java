package org.ilmostro.pure.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import io.netty.channel.ChannelHandler;
import org.ilmostro.pure.annotation.NettyHandler;
import org.ilmostro.pure.socket.PathNotFoundHandler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
public class NettyHandlerSupport implements BeanPostProcessor {

    //一个path可以有多个channel handler来处理。并且是根据连接来确认处理的pipeline
    private final Map<NettyHandler, ChannelHandler> register = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) throws BeansException {
        if(!(bean instanceof ChannelHandler)){
            return bean;
        }
        NettyHandler annotation = AnnotationUtils.findAnnotation(bean.getClass(), NettyHandler.class);
        if(Objects.isNull(annotation)){
            return bean;
        }
        register.put(annotation, (ChannelHandler) bean);
        return bean;
    }

    /**
     * 获取所有自定义的socket路径
     *
     * @return 所有路径
     */
    public List<String> getCustomPipelinePath(){
        if(register.isEmpty()){
            return Collections.emptyList();
        }
        return register.keySet().stream().map(NettyHandler::path).collect(Collectors.toList());
    }

    /**
     * 根据路径获取一个处理器
     *
     * @param key 路径
     * @return 处理器
     */
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
