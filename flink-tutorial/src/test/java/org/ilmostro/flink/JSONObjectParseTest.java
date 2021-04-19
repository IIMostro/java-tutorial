package org.ilmostro.flink;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @author li.bowei
 */

public class JSONObjectParseTest {

    @Test
    public void parse(){
        String value = "{\"money\":938,\"orderId\":\"9cc72fe3-cf87-43bb-b2e2-4e2f1d7b8f04\",\"store\":\"手机\",\"timestamp\":1618453997414,\"uid\":95}";
        JSONObject parse = JSONObject.parseObject(value);
        System.out.println(parse.toJavaObject(OrderEntity.class));
    }
}
