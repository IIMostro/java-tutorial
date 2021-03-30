package org.ilmostro.basic.expression;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 **/
@Slf4j
public class Start {

    public static void main(String[] args) {
        int type  = 2;
        int expression = type == 0 ? 61 : type == 1 ? 73: type == 2 ? 74 : type;
        log.info("expression:{}", expression);
    }
}
