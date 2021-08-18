package org.ilmostro.flink.start.source;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class CustomRichSourceApplication {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(new CustomRichSourceFunction());
        source.map(new CustomRichMapFunction())
                .flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> out.collect(Tuple2.of(StringUtils.split(value, " ")[2], 1)))
                .returns(Types.TUPLE(Types.STRING, Types.INT))
                .keyBy(v1 -> v1.f0)
                .sum(1)
                .print();
        env.execute("CustomRichSourceApplication");
    }
}
