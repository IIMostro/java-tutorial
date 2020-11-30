package org.ilmostro.mqtt.repository;

import org.ilmostro.mqtt.entity.DoorEvent;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * @author li.bowei
 * @date 2020/11/17 11:09
 */
public interface DoorEventTimeRepository extends ReactiveElasticsearchRepository<DoorEvent, Long> {
}
