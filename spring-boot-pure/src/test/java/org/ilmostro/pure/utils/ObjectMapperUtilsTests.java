package org.ilmostro.pure.utils;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author li.bowei
 */
class ObjectMapperUtilsTests {

	@Test
	void toJSONString() {
		MockedStatic<ObjectMapperUtils> mockStatic = Mockito.mockStatic(ObjectMapperUtils.class);
		mockStatic.when(() -> ObjectMapperUtils.toJSONString("")).thenReturn("this is true");
		assertEquals("this is true", ObjectMapperUtils.toJSONString(""));
	}
}
