package org.ilmostro.basic.annotation;

/**
 * @author li.bowei
 */
public @interface Attention {

    String value() default "重点关注";

    Solution solution();

    enum Solution{
        DYNAMIC, BACKTRACK
    }
}
