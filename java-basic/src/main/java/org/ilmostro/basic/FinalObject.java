package org.ilmostro.basic;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author li.bowei
 */
@RequiredArgsConstructor
@ToString
@Getter
@Builder
public class FinalObject {

	private final int id;
	private final String name;
}
