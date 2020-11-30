package org.ilmostro.basic.function;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.Test;

import java.util.Map;

/**
 * @author li.bowei
 * @date 2020/7/23 11:23
 */
@Slf4j
public class CustomFunctionTest {

    @Test
    public void test() {
        CustomFunction function = var1 -> JSON.parseObject(JSON.toJSONString(var1));
        User user = User.supplier();
        Map<String, Object> result = function.generator(user);
        log.info("custom function, param:{}, result:{}", user, result);
    }
}
