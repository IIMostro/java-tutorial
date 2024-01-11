package org.ilmostro.basic.function;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.basic.User;
import org.junit.jupiter.api.Test;

/**
 * 断言函数的简单使用 Predicate函数为一个输入，一个返回，返回必须为boolean类型
 *
 * @author li.bowei
 * @date 2020/7/22 14:30
 */
@Slf4j
public class PredicateTest {

  /** 简单的断言函数 */
  @Test
  public void simple() {
    Predicate<Integer> predicate = var1 -> var1 > 10;
    int number = 1;
    boolean test = predicate.test(number);
    log.info("predicate function, number > 10, param:{}, result:{}", number, test);
    assertFalse(test);
  }

  /** 联合使用的断言函数 */
  @Test
  public void combination() {
    Predicate<Integer> predicate = var1 -> var1 > 10;
    Predicate<Integer> predicate1 = var1 -> var1 < 100;
    Predicate<Integer> and = predicate.and(predicate1);

    int number = 50;
    boolean result = and.test(number);
    log.info("predicate and, number > 10 and number < 100, param:{}, result:{}", number, result);
    assertTrue(result);
  }

  /** 两个参数的判断函数，如果多于两个参数的判断则需要柯里化该函数 */
  @Test
  public void multiple() {
    //noinspection ConstantConditions
    BiPredicate<Integer, String> predicate = (v1, v2) -> v1 > 10 && v2.contains(".");
    int number = 20;
    String str = "ilmostro";
    boolean test = predicate.test(number, str);
    log.info("bi predicate, int > 10, string has ., param: {} {}, result:{}", number, str, test);
    assertFalse(test);
  }

  /** 对象的断言函数 判断用户密码是否大于6位 */
  @Test
  public void object() {
    Predicate<User> predicate = var1 -> var1.getPassword().length() > 6;
    User user = User.supplier();
    boolean result = predicate.test(user);
    log.info("predicate object, user password length > 6, param:{}, result:{}", user, result);
    assertFalse(result);
  }
}
