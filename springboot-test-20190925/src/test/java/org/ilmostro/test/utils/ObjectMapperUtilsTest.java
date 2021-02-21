package org.ilmostro.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    }
}