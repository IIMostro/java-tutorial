package org.ilmostro.basic.annotation;

/**
 * @author li.bowei
 */
public @interface Attention {

    String value() default "重点关注";

    Algorithm[] algorithm() default Algorithm.NONE;

    Structure[] structure() default Structure.NONE;

    enum Algorithm {
        NONE, DYNAMIC, BACKTRACK, STRING, PRE_SUM, MEMORY_SEARCH
    }

    enum Structure{
        NONE, TREE
    }
}
