package org.ilmostro.basic.designpatterns.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author li.bowei
 **/
public class CommonBuilder<T> {

    private final Supplier<T> instantiation;
    private final List<Consumer<T>> modifies = new ArrayList<>();

    public CommonBuilder(Supplier<T> instantiation) {
        this.instantiation = instantiation;
    }

    public static <T> CommonBuilder<T> of(Supplier<T> instance){
        return new CommonBuilder<>(instance);
    }

    public <P1> CommonBuilder<T> with(Consumer1<T, P1> consumer1, P1 p1){
        Consumer<T> consumer = instance -> consumer1.accept((T) instance, p1);
        modifies.add(consumer);
        return this;
    }

    public <P1, P2> CommonBuilder<T> with(Consumer2<T, P1, P2> consumer1, P1 p1, P2 p2){
        Consumer<T> consumer = instance -> consumer1.accept((T) instance, p1, p2);
        modifies.add(consumer);
        return this;
    }

    public <P1, P2, P3> CommonBuilder<T> with(Consumer3<T, P1, P2, P3> consumer1, P1 p1, P2 p2, P3 p3){
        Consumer<T> consumer = instance -> consumer1.accept((T) instance, p1, p2, p3);
        modifies.add(consumer);
        return this;
    }

    public T build(){
        T t = instantiation.get();
        modifies.forEach(var1 -> var1.accept(t));
        modifies.clear();
        return t;
    }


    @FunctionalInterface
    public interface Consumer1<T, P>{
        void accept(T t, P p);
    }

    @FunctionalInterface
    public interface Consumer2<T, P1, P2>{
        void accept(T t, P1 p1, P2 p2);
    }

    @FunctionalInterface
    public interface Consumer3<T, P1, P2, P3>{
        void accept(T t, P1 p1, P2 p2, P3 p3);
    }
}
