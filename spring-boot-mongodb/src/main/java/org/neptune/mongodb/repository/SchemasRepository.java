package org.neptune.mongodb.repository;

import org.neptune.mongodb.domain.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemasRepository extends MongoRepository<Schema, String> {

    Schema findByDeviceIdAndProductKeyAndType(String deviceId, String productKey, String type);
}
