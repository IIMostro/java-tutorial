package org.ilmostro.redis.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author li.bowei
 **/
@Component
public class ObjectMapperUtils implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ObjectMapperUtils.class);

    private volatile static ObjectMapper objectMapper;
    private ApplicationContext applicationContext;

    private static void initialize(){
        if(Objects.isNull(objectMapper)){
            synchronized (ObjectMapperUtils.class){
                if(Objects.isNull(objectMapper)){
                    objectMapper = new ObjectMapper();
                }
            }
        }
    }

    public static String toJSONString(@NonNull Object obj){
        try {
            initialize();
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("object to json string error! parameter:{}, cause:{}", obj, e.getMessage());
            throw new RuntimeException("object to json string error!");
        }
    }

    public static <T> T toJavaObject(@NonNull String json, @NonNull Class<T> clazz) {
        if(StringUtils.isEmpty(json)){
            logger.error("clazz function, json string to java object parameter is empty!");
            throw new IllegalArgumentException("json string to java object parameter is empty!");
        }
        try {
            initialize();
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error("json to java object error! json:{}, cause:{}", json, e.getMessage());
            throw new RuntimeException("json to java object error!");
        }
    }

    public static <T> T toJavaObject(@NonNull String json, @NonNull JavaType javaType) {
        if(StringUtils.isEmpty(json)){
            logger.error("java type function, json string to java object parameter is empty!");
            throw new IllegalArgumentException("json string to java object parameter is empty!");
        }
        try {
            initialize();
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            logger.error("json to java object error! json:{}, cause:{}", json, e.getMessage());
            throw new RuntimeException("json to java object error!");
        }
    }

    public static <T> Collection<T> toJavaArray(@NonNull String json, @NonNull Class<T> clazz) {
        initialize();
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return toJavaObject(json, javaType);
    }

    public static ObjectMapper getObjectMapper() {
        initialize();
        return objectMapper;
    }

    @Override
    public void afterPropertiesSet() {
        objectMapper = applicationContext.getBean(ObjectMapper.class);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
