package org.ilmostro.mqtt.integration;

import org.springframework.integration.annotation.MessagingGateway;

/**
 * @author li.bowei
 * @date 2020/11/16 20:35
 */
@MessagingGateway(defaultRequestChannel = "mqttMessageChannel")
public interface InnerGateWay {

    /**
     * 发送消息
     * @param msg 消息体
     */
    void send(String msg);
}
