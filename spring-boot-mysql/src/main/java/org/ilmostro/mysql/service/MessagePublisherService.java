package org.ilmostro.mysql.service;

import org.ilmostro.mysql.entity.User;

/**
 * @author li.bowei
 */
public interface MessagePublisherService {

    void publisher(User user);
}
