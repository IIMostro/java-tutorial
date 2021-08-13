package org.ilmostro.flink.project.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@ToString
public class Access implements Serializable {

    private transient static final List<String> DEVICES_TYPE_LIST = Arrays.asList("小米", "IPHONE11", "华为", "VIVO", "OPPO");

    private String device;
    private String deviceType;
    private String os;
    private String event;
    private String net;
    private String channel;
    private String uid;
    private int nu;
    private String ip;
    private long time;
    private String version;
    private Product product;
}
