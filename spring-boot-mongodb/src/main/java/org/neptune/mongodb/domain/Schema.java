package org.neptune.mongodb.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("schemas")
public class Schema {

    @Id
    private String id;
    private String deviceId;
    private String productKey;
    private String type;
    private String context;
}
