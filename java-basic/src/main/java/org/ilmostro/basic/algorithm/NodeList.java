package org.ilmostro.basic.algorithm;

import java.util.*;

/**
 * @author li.bowei
 */
public class NodeList {

    private final int val;
    private final List<NodeList> child;

    public NodeList(int val, List<NodeList> child) {
        this.val = val;
        this.child = child;
    }

    public List<NodeList> getChild() {
        return child;
    }

    private static class Hierarchy{
        private final List<NodeList> child;
        private final int hierarchy;

        public Hierarchy(List<NodeList> child, int hierarchy) {
            this.child = child;
            this.hierarchy = hierarchy;
        }

        public List<NodeList> getChild() {
            return child;
        }

        public int getHierarchy() {
            return hierarchy;
        }
    }

    public Collection<NodeList> traverse(Integer hierarchy) {
        Queue<Hierarchy> queue = new LinkedList<>();
        queue.offer(new Hierarchy(child, 0));
        Collection<NodeList> result = new LinkedList<>();
        while(!queue.isEmpty()){
            Hierarchy var1 = queue.poll();
            List<NodeList> child = var1.getChild();
            int hierarchy1 = var1.getHierarchy();
            if(hierarchy1 == hierarchy){
                result.addAll(child);
                continue;
            }
            for (NodeList nodeList : child) {
                queue.offer(new Hierarchy(nodeList.child, hierarchy1 + 1));
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "NodeList{" +
                "val=" + val +
                ", child=" + child +
                '}';
    }

    public static void main(String[] args) {
        NodeList nodeList = new NodeList(1, Collections.singletonList(new NodeList(2, Collections.singletonList(new NodeList(3, null)))));
        Collection<NodeList> traverse = nodeList.traverse(3);
        System.out.println(traverse);
    }
}
