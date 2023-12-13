package org.ilmostro.basic.algorithm.leetcode.string;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author li.bowei
 */
public class CheckIfNumberHasEqualDigitCountAndDigitValue {

	public static void main(String[] args) {
		CheckIfNumberHasEqualDigitCountAndDigitValue service = new CheckIfNumberHasEqualDigitCountAndDigitValue();
		final boolean b = service.digitCount("1210");
		System.out.println(b);
		final boolean b1 = service.digitCount("030");
		System.out.println(b1);
		final boolean b2 = service.digitCount("0");
		System.out.println(b2);
		final boolean b3 = service.digitCount("21200");
		System.out.println(b3);
		final boolean b4 = service.digitCount("5210010007");
		System.out.println(b4);
	}

	public boolean digitCount(String num) {
		if (num.length() == 1){
			return num.toCharArray()[0] == 0;
		}
		Map<Integer, Integer> map = new HashMap<>();
		final char[] chars = num.toCharArray();
		for (char aChar : chars) {
			int i = aChar - '0';
			map.put(i, map.getOrDefault(i, 0) + 1);
		}
		for (int i = 0; i < chars.length - 1; i++) {
			int j = chars[i] - '0';
			if (Objects.isNull(map.get(i)) && j != 0) return false;
			if (map.getOrDefault(i, 0) != j) return false;
		}
		return true;
	}
}
