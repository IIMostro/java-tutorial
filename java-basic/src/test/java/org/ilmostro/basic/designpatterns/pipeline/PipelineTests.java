package org.ilmostro.basic.designpatterns.pipeline;

import org.ilmostro.basic.designpatterns.pipline.*;
import org.junit.jupiter.api.Test;

public class PipelineTests {

    @Test
    void test_execute_pipeline_should_work() throws Exception {
        Pipeline pipeline = new StandardPipeline();
        PipelineValue value = new GraySwitchValue();
        PipelineContext context = new StandardPipelineContext();
        pipeline.addValue(value);
        pipeline.invoke(context);
    }
}
