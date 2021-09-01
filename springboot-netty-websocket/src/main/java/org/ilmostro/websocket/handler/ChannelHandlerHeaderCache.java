package org.ilmostro.websocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author li.bowei
 */
public class ChannelHandlerHeaderCache {

    private static final Logger logger = LoggerFactory.getLogger(ChannelHandlerHeaderCache.class);
    private static final Map<String, Map<String, String>> CHANNEL_HEADERS = new ConcurrentHashMap<>();
    private static final Map<String, String> EMPTY = Collections.unmodifiableMap(new HashMap<>());

    //默认的合并策略
    private static final BiFunction<Map<String, String>, Map<String, String>, Map<String, String>> DEFAULT_BI_MERGE = (v1, v2) -> { v1.putAll(v2);return v1; };

    //获取channel id
    private static final Function<Channel, String> getChannelIdFunction = v1 -> Optional.ofNullable(v1).map(Channel::id).map(ChannelId::asLongText).orElse("");
    private static final Function<ChannelHandlerContext, Channel> getChannelFunction = v1 -> Optional.ofNullable(v1).map(ChannelHandlerContext::channel).orElseThrow(IllegalArgumentException::new);

    /**
     * 获取channel下的头部信息
     *
     * @param channel channel
     * @param key key
     * @return value
     */
    public static String getChannelHeaderValue(Channel channel, String key) {
        return getChannelIdFunction.andThen(CHANNEL_HEADERS::get).andThen(v1 -> v1.get(key)).apply(channel);
    }
    public static final Function<Channel, Function<String, String>> getChannelHeaderValue = v1 -> v2 -> getChannelHeaderValue(v1, v2);

    /**
     * 设置channel的头部信息
     *
     * @param channel channel
     * @param headers 头部信息
     */
    public static void setChannelHeader(Channel channel, Map<String, String> headers) {
        defaultSetHandlerFunction.apply(channel).apply(headers).apply(CHANNEL_HEADERS::merge);
    }
    public static final Function<Channel, Consumer<Map<String, String>>> setChannelHandler = v1 -> v2 -> setChannelHeader(v1, v2);

    @FunctionalInterface
    private interface MergeFunction<K, V> {
        Map<K, K> merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction);
    }

    //通过channel获取头部信息的柯里化表达
    private static final Function<Channel, Function<Map<String, String>,
            Function<MergeFunction<String, Map<String, String>>, Map<String, String>>>> defaultSetHandlerFunction =
            v1 -> v2 -> v3 -> getChannelIdFunction.andThen(id -> v3.merge(id, v2, DEFAULT_BI_MERGE)).apply(v1);

    /**
     * 设置单个头部信息的值
     *
     * @param channel channel
     * @param key key
     * @param value value
     */
    public synchronized static void setChannelHeader(Channel channel, String key, String value) {
        Map<String, String> temp = new HashMap<>();
        temp.put(key, value);
        setChannelHeader(channel, temp);
    }
    public static final Function<ChannelHandlerContext, Function<String, Consumer<String>>> setChannelHeader =
            getChannelFunction.andThen(v1 -> v2 -> v3 -> setChannelHeader(v1, v2, v3));

    /**
     * 获取当前channel上的头信息
     *
     * @param channel channel
     * @return 头信息
     */
    public static Map<String, String> getChannelHeaders(Channel channel) {
        Map<String, String> result = getChannelIdFunction.andThen(CHANNEL_HEADERS::get).apply(channel);
        return CollectionUtils.isEmpty(result) ? EMPTY : result;
    }
    public static final Function<ChannelHandlerContext, Map<String, String>> getChannelHeaders =
            getChannelFunction.andThen(ChannelHandlerHeaderCache::getChannelHeaders);

    public static void clean(Channel channel) {
        getChannelIdFunction.andThen(CHANNEL_HEADERS::remove).apply(channel);
    }
    public static final Consumer<Channel> clean = ChannelHandlerHeaderCache::clean;

    public static void cleanAll() {
        logger.info("websocket headers clean all");
        CHANNEL_HEADERS.clear();
    }
}
