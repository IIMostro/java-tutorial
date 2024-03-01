package org.ilmostro.pure.test;

import org.ilmostro.pure.PureApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PureApplication.class)
public class StorageServiceTests {

    @Test
    void test_save_file_should_work(){
        final var service = StorageServiceFactory.getStorageService("local");
        service.save("test.txt", "Hello, World!");
    }
}
