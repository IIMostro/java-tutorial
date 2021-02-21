package org.ilmostro.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author li.bowei
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ObjectMapperUtilsTest {

    @Test
    public void test(){
        UserEntity user = new UserEntity(1, "ilmostro", 18);

        String jsonString = ObjectMapperUtils.toJSONString(user);
        log.info("to json: data:{}", jsonString);

        UserEntity userEntity = ObjectMapperUtils.toJavaObject(jsonString, UserEntity.class);
        log.info("to java bean: bean:{}", userEntity);

        UserEntity user1 = new UserEntity(2, "ilmostro", 18);
        UserEntity user2 = new UserEntity(3, "ilmostro", 18);
        UserEntity user3 = new UserEntity(4, "ilmostro", 18);

        List<UserEntity> users = Arrays.asList(user, user1, user2, user3);
        String jsons = ObjectMapperUtils.toJSONString(users);
        log.info("to json: data:{}", jsons);
        Collection<UserEntity> users1 = ObjectMapperUtils.toJavaArray(jsons, UserEntity.class);
        log.info("to java list, data:{}", users1);
    }
}