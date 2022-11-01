package org.ilmostro.pure.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class JSONObjectTests {

	@Test
	void map() {

		Map<Integer, Map<Integer, Long>> map = new HashMap<>();

		Map<Integer, Long> parameter = new HashMap<>();
		parameter.put(2, 3L);
		map.put(1, parameter);

		final String json = JSONObject.toJSONString(map, true);
		log.info("Serializable:[\n{}\n]", json);
		final Map<Integer, Map<Integer, Long>> serializable = JSONObject.parseObject(json, new TypeReference<Map<Integer, Map<Integer, Long>>>() { });
		log.info("success:[{}]", serializable);
	}

}
