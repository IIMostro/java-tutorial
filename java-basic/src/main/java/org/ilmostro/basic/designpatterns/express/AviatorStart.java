package org.ilmostro.basic.designpatterns.express;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Options;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li.bowei
 */
@Slf4j
public class AviatorStart {

    static class Add extends AbstractFunction{

        @Override
        public String getName() {
            return "add";
        }

        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Object left = arg1.getValue(env);
            Object right = arg2.getValue(env);
            log.info("left:{}, right:{}, env:{}", left, right, env.keySet());
            double var1 = Double.parseDouble(left.toString());
            double var2 = Double.parseDouble(right.toString());
            return AviatorDecimal.valueOf(var1 +var2);
        }
    }

    public static void main(String[] args) {
        Map<String, Object> param = new HashMap<>();
        param.put("a", 100);
        param.put("b", 200.00000002);
        param.put("c", 2.000003);
        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        Object exec = AviatorEvaluator.execute("a+b*c", param);
        System.out.println(exec);
    }
}
