package org.neptune.elasticsearch.repository;

import org.junit.jupiter.api.Test;
import org.neptune.elasticsearch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;


    @Test
    void test_save_user() {
        // given
        User user = new User();
        user.setId(1L);
        user.setName("ilmostro");
        user.setAge(30);

        // when
        repository.save(user);

        // then
        User saved = repository.findById(1L).get();
        assertNotNull(saved);
        assertEquals(user.getName(), saved.getName());
        assertEquals(user.getAge(), saved.getAge());
    }
}
