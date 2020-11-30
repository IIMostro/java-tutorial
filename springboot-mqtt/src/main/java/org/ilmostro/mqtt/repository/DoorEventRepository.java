package org.ilmostro.mqtt.repository;

import org.ilmostro.mqtt.entity.DoorEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author li.bowei
 * @date 2020/11/17 11:07
 */
public interface DoorEventRepository extends ReactiveMongoRepository<DoorEvent, Long> {
}
