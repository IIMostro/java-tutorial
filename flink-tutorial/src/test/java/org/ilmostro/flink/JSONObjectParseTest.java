package org.ilmostro.flink;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author li.bowei
 */

public class JSONObjectParseTest {

    @Test
    public void parse(){
        String value = "\"{\\\"money\\\":387,\\\"orderId\\\":\\\"35f94c53-668c-4a30-adff-91904d470787\\\",\\\"store\\\":\\\"电脑\\\",\\\"timestamp\\\":1619061446894,\\\"uid\\\":88}\"";
        value = StringUtils.replace(value, "\\\"", "\"");
        value = StringUtils.substringAfter(value, "\"");
        value = StringUtils.substringBeforeLast(value, "\"");
        System.out.println(value);
        JSONObject parse = JSONObject.parseObject(value);
        System.out.println(parse.toJavaObject(OrderEntity.class));
    }
}
