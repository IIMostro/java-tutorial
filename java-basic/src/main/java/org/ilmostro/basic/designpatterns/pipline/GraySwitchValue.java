package org.ilmostro.basic.designpatterns.pipline;

public class GraySwitchValue extends AbstractPipelineValue{

    @Override
    protected boolean doExecute(PipelineContext context) {
        context.put("gray", true);
        return true;
    }
}
