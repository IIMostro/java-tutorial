package org.ilmostro.flink;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.math.BigDecimal;
import java.util.Properties;

public class WordCount {

    public static void main(String[] args) throws Exception {
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.120.20:9092");
        properties.setProperty("zookeeper.connect", "192.168.120.20:2181");
        properties.setProperty("group.id", "custom-consumer-flink");
        //连接socket获取输入的数据
        DataStreamSource<String> stream = env.addSource(new FlinkKafkaConsumer<>("flink-stream-in-topic", new SimpleStringSchema(), properties));

        stream.flatMap((FlatMapFunction<String, State>) (value, collector) -> {
            OrderEntity orderEntity = JSONObject.parseObject(value).toJavaObject(OrderEntity.class);
            collector.collect(new State(orderEntity.getStore(), orderEntity.getMoney()));
        })
                .keyBy(new KeySelector<State, String>() {
                    @Override
                    public String getKey(State state) throws Exception {
                        return state.getName();
                    }
                })
                .reduce(new ReduceFunction<State>() {
                    @Override
                    public State reduce(State state, State t1) throws Exception {
                        return new State(state.getName(), state.getMoney().add(t1.getMoney()));
                    }
                })
                .print();

        env.execute("streaming word count");
    }
}
