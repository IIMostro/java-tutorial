package org.ilmostro.pure.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 2, time = 6)
@Threads(4)
@Fork(2)
@Warmup(iterations = 1)
@State(value = Scope.Benchmark)
public class BasicJMHTests {

    public static void main(String[] args) throws Exception{
        final Options opt = new OptionsBuilder().include(BasicJMHTests.class.getName() + ".")
                // 单线程执行
                .forks(1)
                // 输出结果
                .result("result.json")
                // 结果格式
                .resultFormat(ResultFormatType.JSON)
                // 预热次数
                .warmupIterations(1)
                // 测试次数
                .measurementIterations(2)
                // 测试时间
                .measurementTime(TimeValue.seconds(6))
                .build();
        new Runner(opt).run();
    }

    @Param(value = { "10", "50", "100" })
    private int length;

    @Benchmark
    public void testStringAdd(Blackhole blackhole) {
        String a = "";
        for (int i = 0; i < length; i++) {
            a += i;
        }
        blackhole.consume(a);
    }

    @Benchmark
    public void testStringBuilderAdd(Blackhole blackhole) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i);
        }
        blackhole.consume(sb.toString());
    }
}
