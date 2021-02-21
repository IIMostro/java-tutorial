package org.ilmostro.test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author li.bowei
 **/
@Component
public class ObjectMapperUtils implements ApplicationContextAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ObjectMapperUtils.class);

    private static ObjectMapper objectMapper;
    private ApplicationContext applicationContext;


    public static String toJSONString(@NonNull Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("object to json string error! parameter:{}, cause:{}", obj, e.getMessage());
            throw new RuntimeException("object to json string error!");
        }
    }

    public static <T> T toJavaObject(@NonNull String json, @NonNull Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            logger.error("json string to java object parameter is empty!");
            throw new IllegalArgumentException("json string to java object parameter is empty!");
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("json to java object error! json:{}, cause:{}", json, e.getMessage());
            throw new RuntimeException("json to java object error!");
        }
    }

    public static <T> T toJavaObject(@NonNull String json, @NonNull JavaType javaType) {
        if (StringUtils.isEmpty(json)) {
            logger.error("json string to java object parameter is empty!");
            throw new IllegalArgumentException("json string to java object parameter is empty!");
        }
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error("json to java object error! json:{}, cause:{}", json, e.getMessage());
            throw new RuntimeException("json to java object error!");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        objectMapper = applicationContext.getBean(ObjectMapper.class);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
