package org.neptune.mongodb.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.neptune.mongodb.SpringBootMongodbApplication;
import org.neptune.mongodb.domain.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = SpringBootMongodbApplication.class)
@Slf4j
public class SchemaRepositoryTests {

    @Autowired
    private SchemasRepository repository;

    @Test
    void test_insert_should_work() {
        final var schema = new Schema();
        schema.setDeviceId("xxxxS_tlbw00100830");
        schema.setType("ipc");
        schema.setProductKey("edcbd053-18b");
        schema.setContext("hello world");
        repository.save(schema);
    }

    @Test
    void test_find_should_work() {
        final var watch = new StopWatch();
        watch.start("query");
        final var schema = repository.findByDeviceIdAndProductKeyAndType("xxxxS_jjbi967xaeob", "edcbd053-18b", "ipc");
        watch.stop();
        log.info("schema:{}", schema);
        log.info("explan: {}", watch.getLastTaskTimeMillis());
    }

    @Test
    void test_batch_insert_should_work(){
        List<Schema> schemas = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            final var schema = new Schema();
            schema.setDeviceId("xxxxS_" + generateRandomString(12));
            schema.setType("ipc");
            schema.setProductKey("edcbd053-18b");
            schema.setContext("hello world");
            schemas.add(schema);
            if (i % 10000 == 0){
                repository.insert(schemas);
                schemas.clear();
            }
        }
    }

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
}
