package org.ilmostro.pure.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author li.bowei
 **/
public class CommonSaveUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonSaveUtils.class);

    private CommonSaveUtils() {

    }

    public static <T> InsertFunction<T> insert(final Function<T, Integer> insert) {
        return new InsertFunction<>(insert);
    }

    public static <T, E> InsertBiFunction<T, E> insert(final BiFunction<T, E, Integer> insert){
        return new InsertBiFunction<>(insert);
    }

    public static class InsertFunction<T> {

        private final Function<T, Integer> insert;

        public InsertFunction(Function<T, Integer> insert) {
            this.insert = insert;
        }

        public UpdateFunction<T> update(final Function<T, Integer> update) {
            return new UpdateFunction<>(insert, update);
        }
    }

    public static class InsertBiFunction<T, E> {
        private final BiFunction<T, E, Integer> insert;

        public InsertBiFunction(BiFunction<T, E, Integer> insert) {
            this.insert = insert;
        }

        public UpdateBiFunction<T, E> update(BiFunction<T, E, Integer> update){
            return new UpdateBiFunction<>(insert, update);
        }
    }

    public static class UpdateFunction<T> {

        private final Function<T, Integer> insert;
        private final Function<T, Integer> update;

        public UpdateFunction(Function<T, Integer> insert, Function<T, Integer> update) {
            this.insert = insert;
            this.update = update;
        }

        public Parameter<T> parameter(final T data) {
            return new Parameter<>(insert, update, data);
        }
    }

    public static class UpdateBiFunction<T, E>{
        private final BiFunction<T, E, Integer> insert;
        private final BiFunction<T, E, Integer> update;


        public UpdateBiFunction(BiFunction<T, E, Integer> insert, BiFunction<T, E, Integer> update) {
            this.insert = insert;
            this.update = update;
        }

        public BiParameter<T, E> parameter(T data, E value){
            return new BiParameter<>(insert, update, data, value);
        }

    }

    public static class Parameter<T> {
        private final Function<T, Integer> insert;
        private final Function<T, Integer> update;
        private final T data;

        public Parameter(Function<T, Integer> insert, Function<T, Integer> update, T data) {
            this.insert = insert;
            this.update = update;
            this.data = data;
        }

        public Conditional<T> conditional(final Predicate<T> predicate){
            return new Conditional<>(insert, update, data, predicate);
        }
    }

    public static class BiParameter<T, E>{
        private final BiFunction<T, E, Integer> insert;
        private final BiFunction<T, E, Integer> update;
        private final T data;
        private final E value;

        public BiParameter(BiFunction<T, E, Integer> insert, BiFunction<T, E, Integer> update, T data, E value) {
            this.insert = insert;
            this.update = update;
            this.data = data;
            this.value = value;
        }

        public BiConditional<T, E> conditional(final BiPredicate<T, E> predicate){
            return new BiConditional<>(insert, update,predicate, data, value);
        }
    }

    public static class Conditional<T>{
        private final Function<T, Integer> insert;
        private final Function<T, Integer> update;
        //如果predicate.test() 为true 则执行insert反之执行update
        private final Predicate<T> predicate;
        private final T data;

        private String message = "save parameter error! parameter:{}";
        private RuntimeException exception = new RuntimeException("save error!");

        public Conditional(Function<T, Integer> insert, Function<T, Integer> update,
                           T data, Predicate<T> predicate) {
            this.insert = insert;
            this.update = update;
            this.predicate = predicate;
            this.data = data;
        }

        public Conditional<T> message(String message){
            this.message = message;
            return this;
        }

        public Conditional<T> error(RuntimeException exception){
            this.exception = exception;
            return this;
        }

        public void execute() {
            int symbol;
            //如果predicate.test() 为true 则执行insert反之执行update
            if (predicate.test(data)) {
                symbol = insert.apply(data);
            } else {
                symbol = update.apply(data);
            }
            if (symbol < 1) {
                logger.error(message, data);
                throw exception;
            }
        }
    }

    public static class BiConditional<T, E>{
        private final BiFunction<T, E, Integer> insert;
        private final BiFunction<T, E, Integer> update;
        private final BiPredicate<T, E> predicate;
        private final T data;
        private final E value;

        private String message = "save parameter error! parameter:{}";
        private RuntimeException exception = new RuntimeException("save error!");

        public BiConditional(BiFunction<T, E, Integer> insert,
                             BiFunction<T, E, Integer> update,
                             BiPredicate<T, E> predicate,
                             T data,
                             E value) {
            this.insert = insert;
            this.update = update;
            this.predicate = predicate;
            this.data = data;
            this.value = value;
        }

        public BiConditional<T, E> message(String message){
            this.message = message;
            return this;
        }

        public BiConditional<T, E> error(RuntimeException exception){
            this.exception = exception;
            return this;
        }

        public void execute() {
            int symbol;
            //如果predicate.test() 为true 则执行insert反之执行update
            if (predicate.test(data, value)) {
                symbol = insert.apply(data, value);
            } else {
                symbol = update.apply(data, value);
            }
            if (symbol < 1) {
                logger.error(message, data);
                throw exception;
            }
        }
    }
}
