package org.ilmostro.basic.leetcode.everyday;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author li.bowei
 */
@Slf4j
class EveryDayTests {

	private static final EveryDay arithmetic = new EveryDay();


	@Test
	void countNicePairs() {
//		int[] nums = {42,11,1,97};
		int[] nums = {13,10,35,24,76};
		final int i = arithmetic.countNicePairs(nums);
		log.info("result:{}", i);
	}

	@Test
	void test_count_nice_pairs_v2(){
		//		int[] nums = {42,11,1,97};
		int[] nums = {13,10,35,24,76};
		final int i = arithmetic.countNicePairs_v2(nums);
		log.info("result : [{}]", i);
	}
}