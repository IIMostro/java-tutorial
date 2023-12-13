package org.ilmostro.basic.designpatterns.pipline;

public interface Pipeline {

    boolean invoke(PipelineContext context);

    boolean addValue(PipelineValue value);

    boolean removeValue(PipelineValue value);
}
