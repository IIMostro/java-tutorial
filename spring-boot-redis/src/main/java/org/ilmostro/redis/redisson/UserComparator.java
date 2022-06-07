package org.ilmostro.redis.redisson;

import java.util.Comparator;

import org.ilmostro.redis.domain.User;

/**
 * @author li.bowei
 */
public class UserComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o1.getId() - o2.getId();
	}
}
