package org.ilmostro.start.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.start.entity.WeekDay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ObjectMapperTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test() throws JsonProcessingException {

        String json = "[\n" +
                "{\n" +
                "  \"sex\": \"12\",\n" +
                "  \"weekDay\": \"1,2,3\",\n" +
                "  \"weekTimes\": [\n" +
                "    {\n" +
                "      \"name\": \"1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"2\"\n" +
                "    }\n" +
                "  ]\n" +
                "},\n" +
                "{\n" +
                "  \"sex\": \"12\",\n" +
                "  \"weekDay\": \"1,2,3\",\n" +
                "  \"weekTimes\": [\n" +
                "    {\n" +
                "      \"name\": \"1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"2\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "]";

        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, WeekDay.class);

        List<WeekDay> days = objectMapper.readValue(json, javaType);

        for(WeekDay var1:days){
            log.info("var1: {}", var1);
        }
    }
}
