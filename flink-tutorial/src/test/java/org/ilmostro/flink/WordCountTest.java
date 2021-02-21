package org.ilmostro.flink;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.ilmostro.flink.sink.CustomMongoSink;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Properties;

public class WordCountTest {

    private static final Logger logger = LoggerFactory.getLogger(WordCountTest.class);

    @Test
    public void test() throws Exception {
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.120.20:9092");
        properties.setProperty("zookeeper.connect", "192.168.120.20:2181");
        properties.setProperty("group.id", "custom-consumer-flink");
        //连接socket获取输入的数据
        DataStreamSource<String> stream = env.addSource(new FlinkKafkaConsumer<>("flink-stream-in-topic", new SimpleStringSchema(), properties));

        stream.flatMap((FlatMapFunction<String, Tuple2<String, BigDecimal>>) (value, collector) -> {
            logger.info("value:{}", value);
            OrderEntity orderEntity = JSONObject.parseObject(value).toJavaObject(OrderEntity.class);
            collector.collect(new Tuple2<>(orderEntity.getStore(), orderEntity.getMoney()));

        })
                .returns(TypeInformation.of(new TypeHint<Tuple2<String, BigDecimal>>() {
                }))
                .keyBy((KeySelector<Tuple2<String, BigDecimal>, String>) stringBigDecimalTuple2 -> stringBigDecimalTuple2.f0)
                .reduce((ReduceFunction<Tuple2<String, BigDecimal>>) (t1, t2) -> new Tuple2<>(t1.f0, t1.f1.add(t2.f1)))
                .addSink(new CustomMongoSink());

        env.execute("streaming word count");
    }
}
