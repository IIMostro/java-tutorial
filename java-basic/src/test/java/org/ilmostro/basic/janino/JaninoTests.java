package org.ilmostro.basic.janino;

import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;
import org.junit.jupiter.api.Test;

public class JaninoTests {


    @Test
    void test_sample() throws Exception{
        ScriptEvaluator evaluator = new ExpressionEvaluator();
        evaluator.cook("1>=3");
        final var evaluate = evaluator.evaluate(null);
        System.out.println(evaluate);
    }
}
