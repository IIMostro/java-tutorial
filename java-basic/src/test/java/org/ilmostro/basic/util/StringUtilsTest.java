package org.ilmostro.basic.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.ilmostro.basic.User;
import org.junit.Test;
import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei
 */
@Slf4j
public class StringUtilsTest {

    @Test
    public void substringAfterLast() {
        String filename = "有道.xlsx";
        String suffix = StringUtils.substringAfterLast(filename, ".");
        log.info("filename:{}, suffix:{}", filename, suffix);
    }

    @Test
    public void isEmail() {
        String regex = "^(\\w+)(\\.\\w+)*@(\\w+)(\\.\\w+)*.(\\w+)$";
        String email = "encounterallen@qq.com";
        log.info("{}: [{}]", email, email.matches(regex));
        email = "encounterallen@qq";
        log.info("{}:[{}]", email, email.matches(regex));
    }

    @Test
    public void random(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            sb.append(RandomUtils.nextInt(1, 9));
        }
        log.info("sb:{}", sb.toString());
    }

    @Test
    public void format(){
        String[] message = new String[]{"a{1}"};
        String format = MessageFormat.format(message[0], ArrayUtils.subarray(message, 1, message.length));
        log.info(format);

        log.info("{}", Integer.toBinaryString(1<<3));
    }

    @Test
    public void logFormat(){
        String[] message = new String[]{"{}"};
        String message1 = MessageFormatter.arrayFormat(message[0], ArrayUtils.subarray(message, 1, message.length)).getMessage();
        log.info(message1);
    }


    @Test
    public void sort(){

        User supplier = User.supplier();
        supplier.setId(1);
        User supplier1 = User.supplier();
        supplier1.setId(2);

        List<User> collect = Stream.of(supplier, supplier1).sorted().collect(Collectors.toList());
        log.info("{}",collect);

        log.info("{}", 1L << 43);

        log.info("");
    }

}
