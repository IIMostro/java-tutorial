package org.ilmostro.test.template.function.operator;

import java.util.function.BinaryOperator;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
public class OperatorMethod {

    /**
     * 接受两个string,返回一个string
     *
     * @return 拼接两个String
     */
    public BinaryOperator<String> getBinaryOperator(){
        return (var1, var2) -> var1 + var2;
    }
}
