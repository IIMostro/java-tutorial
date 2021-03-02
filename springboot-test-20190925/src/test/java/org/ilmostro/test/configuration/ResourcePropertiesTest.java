package org.ilmostro.test.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author li.bowei
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourcePropertiesTest {

    @Test
    public void read() throws IOException {
        Properties properties = new Properties();
        InputStream in = ResourcePropertiesTest.class.getClassLoader().getResourceAsStream("test.properties");
        properties.load(in);
        String test1 = properties.getProperty("test1");
    }
}