package org.ilmostro.basic.algorithm;

import java.util.*;
import java.util.stream.Collectors;

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

    public Collection<NodeList> traverse(Integer hierarchy) {
        if(hierarchy <= 0){
            return Collections.singletonList(this);
        }
        return recursion(child, hierarchy);
    }

    private Collection<NodeList> recursion(Collection<NodeList> child, int time){
        if(time <= 1 || (Objects.isNull(child) || child.isEmpty())){
            return child;
        }
        List<NodeList> list = child.stream()
                .map(NodeList::getChild)
                .filter(var1 -> Objects.nonNull(var1) && !var1.isEmpty())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if(list.isEmpty()){
            return Collections.emptyList();
        }
        return recursion(list, --time);
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
        Collection<NodeList> traverse = nodeList.traverse(1);
        System.out.println(traverse);
    }
}
