package org.ilmostro.start.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author li.bowei
 * @date 2020/10/16 10:04
 */
public class ListEntityStart {

    public static void main(String[] args) {

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6);

        ListEntity var1 = new ListEntity();
        var1.setIndex(1);
        var1.setData(data);

        ListEntity var2 = new ListEntity();
        var2.setIndex(2);
        var2.setData(data);

        List<ListEntity> list = Arrays.asList(var1, var2);

        for(ListEntity var3: list){
            List<Integer> data1 = var3.getData();
            data1.replaceAll(var4 -> var4+1);
        }

        list.stream().map(ListEntity::getData).flatMap(Collection::stream).forEach(System.out::print);
    }
}
