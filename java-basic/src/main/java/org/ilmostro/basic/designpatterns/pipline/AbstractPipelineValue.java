package org.ilmostro.basic.designpatterns.pipline;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractPipelineValue implements PipelineValue{

    @Override
    public boolean execute(PipelineContext context) {
        log.info("{} start execute", this.getClass().getSimpleName());
        final var result = doExecute(context);
        log.info("{} end execute", this.getClass().getSimpleName());
        return result;
    }

    protected abstract boolean doExecute(PipelineContext context);
}
