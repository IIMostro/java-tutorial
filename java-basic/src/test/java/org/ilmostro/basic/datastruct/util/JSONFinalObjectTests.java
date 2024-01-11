package org.ilmostro.basic.datastruct.util;

import com.alibaba.fastjson.JSON;
import org.ilmostro.basic.FinalObject;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
public class JSONFinalObjectTests {

  @Test
  public void test() {
    final FinalObject object = FinalObject.builder().id(1).name("name").build();
    final String json = JSON.toJSONString(object);
    System.out.println(json);
    final FinalObject finalObject = JSON.parseObject(json, FinalObject.class);
    System.out.println(finalObject);
  }
}
