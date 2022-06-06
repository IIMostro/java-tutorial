package org.ilmostro.redis.redisson.structure;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li.bowei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ranking implements Comparator<Ranking> {

	private Integer uid;
	private Integer rank;

	@Override
	public int compare(Ranking o1, Ranking o2) {
		return o1.rank - o2.rank;
	}
}
