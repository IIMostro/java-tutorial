package org.ilmostro.flink.start.batch;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;

import java.net.URL;

public class BatchWordCount {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        URL path = BatchWordCount.class.getResource("1628065496.txt");
        DataSource<String> source = env.readTextFile(path.toString());
        source.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> {
            for (String item : StringUtils.split(value)) {
                item = StringUtils.trim(item)
                        .toLowerCase()
                        .replace("\"", "")
                        .replace("“", "")
                        .replace(".", "")
                        .replace("”", "")
                        .replace("?", "")
                        .replace("‘", "")
                        .replace("’", "")
                        .replace("!", "");
                out.collect(new Tuple2<>(item, 1));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.INT))
                .groupBy(0)
                .sum(1)
                .print();
    }
}
