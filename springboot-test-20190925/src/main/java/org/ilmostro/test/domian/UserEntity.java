package org.ilmostro.test.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li.bowei on 2019-10-12.
 * @description 测试实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Integer id;
    private String name;
    private Integer age;
}
