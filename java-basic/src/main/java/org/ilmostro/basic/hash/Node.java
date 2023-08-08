package org.ilmostro.basic.hash;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private final String domain;

    private String ip;
    private int count = 0;

    private final Map<String, String> data = new HashMap<>();

    public Node(String domain) {
        this.domain = domain;
        //this.ip=ip;
    }

    public void put(String key, String value) {
        data.put(key, value);
        count++;
    }

    public void remove(String key) {
        data.remove(key);
        count--;
    }

    public String get(String key) {
        return data.get(key);
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return domain;
    }
}
