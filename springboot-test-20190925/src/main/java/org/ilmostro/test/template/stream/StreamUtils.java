package org.ilmostro.test.template.stream;

import org.ilmostro.test.domian.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author li.bowei on 2019-10-12.
 * @description
 */
public class StreamUtils {

    private static final Integer LENGTH = 1000;

    /**
     * 生成1000个Integer类型的list
     *
     * @return collection
     */
    public static Collection<Integer> generate() {
        //Stream.generate方法需要一个Supplier的函数，限制1000个，不然就是一个无限流。
        return Stream.generate(new Random()::nextInt).limit(LENGTH).collect(Collectors.toList());
    }

    /**
     * 随机生成1000个用户
     * @return 用户信息
     */
    public static Collection<UserEntity> getUsers() {
        ArrayList<UserEntity> result = new ArrayList<>(LENGTH);

        for (int index = 0; index < LENGTH; index++) {
            UserEntity userEntity = new UserEntity(index, randomString(5), index + 1);
            result.add(userEntity);
        }
        return result;
    }

    /**
     * 获取随机字符串
     *
     * @param length 长度
     * @return 字符
     */
    private static String randomString(int length){
        if(length<=0){
            length = 10;
        }else if(length >50){
            length = 50;
        }
        StringBuilder s = new StringBuilder(50);
        Random random = new Random();
        for( int i = 0; i < length; i ++) {
            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写还是小写
            s.append((char)(choice + random.nextInt(26)));
        }
        return s.toString();
    }
}
