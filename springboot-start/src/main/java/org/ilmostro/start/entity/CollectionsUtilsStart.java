package org.ilmostro.start.entity;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author li.bowei
 * @date 2020/10/20 10:04
 */
public class CollectionsUtilsStart {

    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Collection<Integer> select = CollectionUtils.select(integers, var1 -> var1.equals(1));
        select.forEach(System.out::print);
    }
}
