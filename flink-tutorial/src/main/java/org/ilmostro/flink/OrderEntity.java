package org.ilmostro.flink;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class OrderEntity {

    private static final String[] STORES = new String[]{"手机", "电脑", "IPAD", "保温杯"};
    private static final Random random = new Random();

    private String orderId;
    private Integer uid;
    private String store;
    private BigDecimal money;
    private Long timestamp;

    public static OrderEntity getInstance(){
        OrderEntity order = new OrderEntity();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUid((int)(Math.random() * 100));
        int index = random.nextInt(3 + 1);
        order.setStore(STORES[index]);
        int money = random.nextInt(1000 + 1);
        order.setMoney(BigDecimal.valueOf(money));
        order.setTimestamp(System.currentTimeMillis());

        return order;
    }

    public static String[] getSTORES() {
        return STORES;
    }

    public static Random getRandom() {
        return random;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
