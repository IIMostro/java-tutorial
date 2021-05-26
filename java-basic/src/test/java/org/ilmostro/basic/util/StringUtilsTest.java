package org.ilmostro.basic.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class StringUtilsTest {

    @Test
    public void substringAfterLast(){
        String filename = "有道.xlsx";
        String suffix= StringUtils.substringAfterLast(filename, ".");
        log.info("filename:{}, suffix:{}", filename, suffix);
    }
}
