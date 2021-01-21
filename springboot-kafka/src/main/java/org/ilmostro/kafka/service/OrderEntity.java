package org.ilmostro.kafka.service;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
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
}
