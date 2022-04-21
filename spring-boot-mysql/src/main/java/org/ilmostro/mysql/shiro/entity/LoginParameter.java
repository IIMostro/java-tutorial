package org.ilmostro.mysql.shiro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author li.bowei
 **/
@Getter
@Setter
@ToString
public class LoginParameter {

    private String username;
    private String password;
}
