package org.ilmostro.flink.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SocketTextStreamFunction;

public class StreamWordCount {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(new SocketTextStreamFunction("192.168.205.20", 5000, ",", 1));
        source.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, collector) -> collector.collect(new Tuple2<>(value, 1)))
                .returns(Types.TUPLE(Types.STRING, Types.INT))
                .keyBy(var1 -> var1.f0)
                .sum(1)
                .print();
        env.execute("start");
    }
}
