package org.ilmostro.basic.designpatterns.pipline;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StandardPipeline implements Pipeline {

    private final List<PipelineValue> values = new ArrayList<>();

    @Override
    public boolean invoke(PipelineContext context) {
        boolean result = true;
        for (PipelineValue value : values) {
            try {
                result &= value.execute(context);
            } catch (Exception ex) {
                log.error("pipeline invoke error: {}", ex.getMessage());
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean addValue(PipelineValue value) {
        if (values.contains(value)) {
            return false;
        }
        return values.add(value);
    }

    @Override
    public boolean removeValue(PipelineValue value) {
        return values.remove(value);
    }
}
