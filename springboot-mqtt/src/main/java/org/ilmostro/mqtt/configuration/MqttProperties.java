package org.ilmostro.mqtt.configuration;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author li.bowei
 * @date 2020/11/16 19:26
 */
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttProperties {

    private String url = "tcp://localhost:1883";
    private String username = "";
    private String password = "";
    private Integer connectionTimeout = 30;
    private Integer keepAliveInterval = 60;

    private MqttChannelProperties inner;

    public MqttConnectOptions getOptions(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{url});
        options.setConnectionTimeout(connectionTimeout);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setKeepAliveInterval(keepAliveInterval);
        return options;
    }

    @Data
    public static class MqttChannelProperties{
        private String topics;
        private Integer completionTimeout = 5000;
        private int qos;
    }
}
