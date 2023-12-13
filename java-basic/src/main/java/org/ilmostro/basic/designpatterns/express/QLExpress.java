package org.ilmostro.basic.designpatterns.express;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 * @author li.bowei
 */
public class QLExpress {

    public static void main(String[] args) throws Exception {
        ExpressRunner runner = new ExpressRunner(true, true);
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("a", 100);
        context.put("b", 200);
        context.put("c", 3);

        String express = "(a+b)*c";
        Object execute = runner.execute(express, context, null, true, true);
        System.out.println(execute);
    }
}
