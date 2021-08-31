package org.ilmostro.websocket.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author li.bowei
 */
public class ChannelHandlerHeaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ChannelHandlerHeaderUtils.class);
    private static final Map<String, Map<String, String>> CHANNEL_HEADERS = new ConcurrentHashMap<>();

    private static final BiFunction<Map<String, String>, Map<String, String>, Map<String, String>> DEFAULT_BI_MERGE = (v1, v2) -> {
        v1.putAll(v2);
        return v1;
    };


    public static String getChannelHeaderValue(String channelId, String key) {
        if (StringUtils.isAnyBlank(channelId, key)) {
            throw new IllegalArgumentException();
        }
        return Optional.of(channelId).map(CHANNEL_HEADERS::get).map(var1 -> var1.get(key)).orElse("");
    }

    public static void setChannelHeader(String channelId, Map<String, String> headers) {
        CHANNEL_HEADERS.merge(channelId, headers, DEFAULT_BI_MERGE);
    }

    public static void setChannelHeader(String channelId, String key, String value) {
        Map<String, String> temp = new HashMap<>();
        temp.put(key, value);
        CHANNEL_HEADERS.merge(key, temp, DEFAULT_BI_MERGE);
    }

    public static Map<String, String> getChannelHeaders(String channelId){
        return CHANNEL_HEADERS.get(channelId);
    }

    public static void clean(String channelId){
        logger.info("websocket headers clean channelId:{}", channelId);
        CHANNEL_HEADERS.remove(channelId);
    }

    public static void cleanAll(){
        logger.info("websocket headers clean all");
        CHANNEL_HEADERS.clear();
    }
}
