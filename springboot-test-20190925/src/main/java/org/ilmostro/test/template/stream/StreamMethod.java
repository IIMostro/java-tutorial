package org.ilmostro.test.template.stream;

import org.ilmostro.test.domian.OrganizationEntity;
import org.ilmostro.test.domian.UserEntity;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * @author li.bowei on 2019-10-12.
 * @version java8
 * @description java8 stream 操作
 * <ul>
 *     <ui>1.中间操作：map, flatMap, peek, filter</ui>
 *     <ui>2.终止操作：sorted, forEach, collect, reduce, match, findAny, findFirst</ui>
 *
 *     <ui>在stream操作中一般不在中间操作中抛出异常</ui>
 * </ul>
 */
public class StreamMethod {

    /**
     * 测试list
     */
    private Collection<Integer> data = StreamUtils.generate();
    private Collection<UserEntity> users = StreamUtils.getUsers();

    /**
     * map 操作，stream中map操作为转化数据
     *
     * @return 将data中的每一个数据+1
     */
    public Collection<Integer> map() {
        return data.stream().map(var1 -> var1 + 1).collect(Collectors.toList());

        //此处var1可以理解为users里面的单个元素。里面的操作相当于是对单个元素的操作
        //users.stream().map(var1 -> var1.getId()).collect(Collectors.toList());
        //与下面的方法等价
        //map中需要传递一个Function<U,R extend list entity>，使用类名::方法名
        //return users.stream().map(UserEntity::getId).collect(Collectors.toList());
    }

    /**
     * flatMap操作
     *
     * @return 两个组织的所有人
     */
    public Collection<UserEntity> flatMap() {
        OrganizationEntity organization1 = new OrganizationEntity();
        OrganizationEntity organization2 = new OrganizationEntity();

        organization1.setUsers(users);
        organization2.setUsers(StreamUtils.getUsers());

        List<OrganizationEntity> organizations = Arrays.asList(organization1, organization2);
        //flatMap 扁平化操作，将同类型的stream合并成一个
        return organizations.stream().flatMap(var1 -> var1.getUsers().stream()).collect(Collectors.toList());
    }

    /**
     * 过滤
     *
     * @return 过滤后的值
     */
    public Collection<Integer> filter() {

        //一般操作
        //for(Integer var1: list){
        //  if(var1 < 0){
        //    list.remove(var1);
        //  }
        //}
        //return list;

        //filter 中需要传递一个Predicate函数,也可以这么写
        //Predicate<Integer> predicate = var1 -> var1 > 0;
        //list.stream().filter(predicate).collect(Collectors.toList())
        return data.stream().filter(var1 -> var1 > 0).collect(Collectors.toList());
    }

    /**
     * 合起来的操作，例如将list中所有数相加
     *
     * @return 和
     */
    public Integer reduce() {
        //获取年纪最大的用户信息
        //Optional<UserEntity> reduce = users.stream().reduce((o1, o2) -> var1.getAge() > var2.getAge() ? var1 : var2);

        //reduce返回一个Optional对象，关于Optional的操作可查看com.hooenergy.demo.optional.OptionalMethod
        return data.stream().reduce((o1, o2) -> o1 + o2).orElse(0);
    }

    /**
     * 排序
     *
     * @return 排序好的列表
     */
    public Collection<Integer> sort() {
        //使用Comparator.comparing排序，可在此函数后加.reversed()方法即反过来
        return data.stream().sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
    }

    /**
     * 收集器
     *
     * @return 搜集好的list
     */
    public Map<Integer, List<UserEntity>> collection() {
        //最常用的为Collectors.toList(),Collectors.toSet();
        //此方法功能强大，但是容易将代码变复杂。建议使用普通的统计功能
        //按年纪分类,key为年纪,value为用户信息
        return users.stream().collect(Collectors.groupingBy(UserEntity::getAge));
    }

    /**
     * 并行流，可以指定forkJoinPool执行，如果不指定线程池则默认使用ForkJoinPool.commonPool()
     */
    public void parallel(){
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        //在多线程中执行stream.parallel的操作
        Set<Integer> invoke = forkJoinPool.submit(() -> data.stream().parallel().map(Objects::hashCode).collect(Collectors.toSet())).invoke();
    }
}
