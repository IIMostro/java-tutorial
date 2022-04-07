package org.ilmostro.pure.mockito;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author li.bowei
 */
public class MockitoTest {

	@Mock
	private Random mockRandom;

	@Before
	public void before(){
		MockitoAnnotations.openMocks(this);
		Mockito.when(mockRandom.nextInt()).thenReturn(100);
	}

	@Test
	public void random(){
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt()).thenReturn(100);
		assertEquals(100, random.nextInt());
	}

	@Test
	public void mockRandom(){
		assertEquals(100, mockRandom.nextInt());
	}

	@Test
	public void mockFunction(){
		BigDecimal mock = Mockito.mock(BigDecimal.class);
		Mockito.when(mock.add(BigDecimal.valueOf(10))).thenReturn(BigDecimal.valueOf(20));
		assertEquals(mock.add(BigDecimal.valueOf(10)), BigDecimal.valueOf(20));

		// 调用真实的方法
		Mockito.when(mock.add(BigDecimal.ONE)).thenCallRealMethod();
		assertEquals(mock.add(BigDecimal.ONE), BigDecimal.ONE);
	}
}
