package org.ilmostro.test.template.function.function;

/**
 * @author li.bowei on 2019-10-12.
 * @description 自定义函数的使用
 * <ul>
 *     <ui><p>@FunctionalInterface注释表示此接口为一个函数接口</ui>
 *     <ui>此注释非必须，如果需要加此注释，则此方法中必须只有一个方法，详情可查看官方文档</ui>
 *     <ui>使用和其他函数一致，定义的返回值以及参数</ui>
 * </ul>
 * @see FunctionalInterface
 */
@FunctionalInterface
public interface FunctionMethodInterface {

    String generator(Double param);
}
