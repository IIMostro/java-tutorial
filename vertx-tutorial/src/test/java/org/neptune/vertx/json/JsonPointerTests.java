package org.neptune.vertx.json;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.pointer.JsonPointer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonPointerTests {

    private static final Logger logger = LoggerFactory.getLogger(JsonPointerTests.class);

    @Test
    void test_create_should_work(){
        final var pointer = JsonPointer.from("/hello/world");
        final var entries = new JsonObject();
        pointer.writeJson(entries, "value", true);
        logger.info("entries: {}", entries.encodePrettily());
    }
}
