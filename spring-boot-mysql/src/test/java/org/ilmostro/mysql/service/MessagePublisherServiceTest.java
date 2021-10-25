package org.ilmostro.mysql.service;

import org.ilmostro.mysql.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessagePublisherServiceTest {

    @Autowired
    private MessagePublisherService service;

    @Test
    public void publisher(){
        User user = new User();
        user.setAge(10);
        user.setName("neptune");
        service.publisher(user);
    }

}