package org.ilmostro.mqtt.configuration;

import org.ilmostro.mqtt.integration.InnerGateWay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author li.bowei
 * @date 2020/11/16 20:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MqttServerConfigurationTest {

    @Autowired
    private InnerGateWay gateWay;

    @Test
    public void send(){
        gateWay.send("this is simple message!");
    }

}
