package org.ilmostro.start.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author li.bowei
 * @date 2020/11/4 11:06
 */
@Data
@ToString
public class User {

    private Integer id;
    private String name;
    private Integer age;
}
