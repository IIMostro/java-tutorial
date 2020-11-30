package org.ilmostro.test.template.optional;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.test.domian.UserEntity;

import java.util.Objects;
import java.util.Optional;

/**
 * @author li.bowei on 2019-10-12.
 * @description 神器Optional的使用
 * <ul>
 *     <ui>使用该方法经量避免使用get方法，get方法如果为空依然会报出NPE</ui>
 * </ul>
 */
@Slf4j
public class OptionalMethod {

    private UserEntity user;

    public void setUser(){
        this.user = new UserEntity(1, "li.bowei", 18);
    }

    public void simple(){
        //获取用户名称，如果不能判断对象是否为空，则需要使用ofNullable来创建Optional对象，
        //如果对象为空，Optional后面的操作不会执行。
        //建议在创建此对象时尽量避免在里面使用 . 操作。而是在之后的操作中使用map来获取对象的操作
        String name = Optional.ofNullable(user).map(UserEntity::getName).orElse(null);

        UserEntity user1 = new UserEntity();

        //orElse标识一个选择操作，如果对象对空则使用另外一个,参数为一个对象
        UserEntity userEntity = Optional.ofNullable(user).orElse(user1);
        //与orElse功能一样，但是此方法需要传递一个提供者方法。
        Optional.ofNullable(user).orElseGet(UserEntity::new);
        //如果对象为空则抛出异常
//        Optional.ofNullable(user).orElseThrow(RuntimeException::new);

        setUser();
        //判断该用户的年纪是否小于19岁。 isPresent，如果操作之后的数据不为null则返回true, 为空返回false
        boolean present = Optional.of(user).filter(var1 -> var1.getAge() < 19).isPresent();

        //如果该对象不为空则打印该对象, ifPresent， 如果该对象不为空则执行
        Optional.of(user).map(Objects::toString).ifPresent(log::info);

    }
}
