package org.ilmostro.basic.datastruct.hash;

import java.util.*;

public class ConsistentHashing {

    //待添加入Hash环的真实服务器节点列表
    private final LinkedList<Node> realNodes = new LinkedList<>();
    //虚拟节点列表
    private final SortedMap<Integer, Node> sortedMap = new TreeMap<>();
    private final int replicas;

    public ConsistentHashing(int replicas, List<Node> nodes) {
        this.replicas = replicas;
        realNodes.addAll(nodes);
        for (Node node : realNodes) {
            for (int i = 1; i <= replicas; i++) {
                String nodeName = node.getName() + "-VM" + i;
                int hash = getHash(nodeName); //nodeName.hashCode();
                sortedMap.put(hash, node);
                System.out.println("虚拟节点hash:" + hash + "【" + nodeName + "】放入");
            }
        }
    }

    public void addNode(Node node) {
        realNodes.add(node);
        for (int i = 0; i < this.replicas; i++) {
            String nodeName = node.getName() + "-VM" + i;
            int hash = getHash(nodeName); //nodeName.hashCode();
            sortedMap.put(hash, node);
            System.out.println("虚拟节点hash:" + hash + "【" + nodeName + "】放入");
        }
    }

    public void removeNode(String name) {
        Iterator<Node> iterator = realNodes.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.getName().equals(name)) {
                iterator.remove();
            }
        }
        for (int i = 0; i < this.replicas; i++) {
            String nodeName = name + "-VM" + i;
            int hash = getHash(nodeName); //nodeName.hashCode();
            sortedMap.remove(hash);
            System.out.println("虚拟节点hash:" + hash + "【" + nodeName + "】移除");
        }
    }

    //使用FNV1_32_HASH算法计算服务器的Hash值

    private static int getHash(String str) {
        // int hash = str.hashCode();

        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }


    //得到应当路由到的结点
    private Node getServer(String key) {
        //得到该key的hash值
        int hash = getHash(key);
        //得到大于该Hash值的所有Map
        Node server;
        SortedMap<Integer, Node> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = sortedMap.firstKey();
            //返回对应的服务器
            server = sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            server = subMap.get(i);
        }

        if (server != null) {
            server.put(key, hash + "");
            System.out.println(server.getName());
        }
        return server;
    }

    //获取实际服务器上负载平均值
    public static double getAverage(LinkedList<Node> arr) {
        double sum = 0;
        int number = arr.size();
        for (int i = 0; i < number; i++) {
            Node node = arr.get(i);

            sum += node.getCount();
        }
        return sum / number;
    }

    //获取实际服务器上负载的标准差
    public static double getStandardDevition(LinkedList<Node> arr) {
        double sum = 0;
        int number = arr.size();
        double avgValue = getAverage(arr);//获取平均值
        for (int i = 0; i < number; i++) {
            Node node = arr.get(i);
            sum += Math.pow((node.getCount() - avgValue), 2);
        }

        return Math.sqrt((sum / (number - 1)));
    }


    public static void main(String[] args) {

        final var hashing = new ConsistentHashing(100, List.of(new Node("A"), new Node("B"), new Node("C")));
        //模拟一百万用户
        for (int i = 0; i < 1000000; i++) {
            String key = "User:" + i;
            System.out.println("[" + key + "]的hash值为" + getHash(key) + ", 被路由到结点[" + hashing.getServer(key).getName() + "]");
        }

        //打印 Node的实际负载
        for (int i = 0; i < hashing.realNodes.size(); i++) {
            Node node = hashing.realNodes.get(i);
            System.out.println(node.getName() + "-> count：" + node.getCount());
        }

        System.out.println("标准差：" + getStandardDevition(hashing.realNodes));
    }
}
