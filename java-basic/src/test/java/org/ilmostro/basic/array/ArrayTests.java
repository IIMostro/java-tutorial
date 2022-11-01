package org.ilmostro.basic.array;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class ArrayTests {

	@Test
	public void test(){
		int[] status = new int[]{1,2,3,4};
		final int[] newStatus = Arrays.copyOf(status, status.length + 1);
		newStatus[newStatus.length - 1] = 5;
		for (int i : newStatus) {
			log.info("i:[{}]", i);
		}
	}
}
